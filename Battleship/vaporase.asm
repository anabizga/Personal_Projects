.586
.model flat, stdcall
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;includem biblioteci, si declaram ce functii vrem sa importam
includelib msvcrt.lib
extern exit: proc
extern malloc: proc
extern memset: proc
extern printf: proc

includelib canvas.lib
extern BeginDrawing: proc
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;declaram simbolul start ca public - de acolo incepe executia
public start
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;sectiunile programului, date, respectiv cod
.data
;aici declaram date
window_title DB "VAPORASE",0
area_width EQU 1000
area_height EQU 550

nr_line EQU 10
nr_cols EQU 11
cel_size EQU 50
area DD 0
matrix DD 450 dup(0)
matrix_dim EQU 10
coord_x DD 0
coord_y DD 0
coord DD 0
i DD 0
j DD 0
linie DD 0
tabla_initializata DD 0
vaporase_nedescoperite DD 0
lovituri_succes DD 0
ratari DD 0
vaporas EQU 1
random1 DD 0
random2 DD 0
nr_vaporase DD 0
ok DD 0

counter DD 0 ; numara evenimentele de tip timer

arg1 EQU 8
arg2 EQU 12
arg3 EQU 16
arg4 EQU 20

symbol_width EQU 10
symbol_height EQU 20
include digits.inc
include letters.inc

array_litera DD 'A','B','C','D','E','F','G','H','I','J'
array_cifra DD '1','2','3','4','5','6','7','8','9'

ROZ EQU 0F0D2DAh
ALBASTRU EQU 4EAFDDh
ROSU EQU 0BE1813h
VERDE EQU 32BE13h

format DB "%d ", 13, 10, 0

.code
; procedura make_text afiseaza o litera sau o cifra la coordonatele date
; arg1 - simbolul de afisat (litera sau cifra)
; arg2 - pointer la vectorul de pixeli
; arg3 - pos_x
; arg4 - pos_y

afisare macro nr
	pusha
	push nr
	push offset format
	call printf
	add esp, 8
	popa
endm

make_text proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, 'A'
	jl make_digit
	cmp eax, 'Z'
	jg make_digit
	sub eax, 'A'
	lea esi, letters
	jmp draw_text
make_digit:
	cmp eax, '0'
	jl make_space
	cmp eax, '9'
	jg make_space
	sub eax, '0'
	lea esi, digits
	jmp draw_text
make_space:	
	mov eax, 26 ; de la 0 pana la 25 sunt litere, 26 e space
	lea esi, letters
	
draw_text:
	mov ebx, symbol_width
	mul ebx
	mov ebx, symbol_height
	mul ebx
	add esi, eax
	mov ecx, symbol_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, symbol_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, symbol_width
bucla_simbol_coloane:
	cmp byte ptr [esi], 0
	je simbol_pixel_alb
	mov dword ptr [edi], 0
	jmp simbol_pixel_next
simbol_pixel_alb:
	mov dword ptr [edi], 0FFFFFFh
simbol_pixel_next:
	inc esi
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_text endp

; un macro ca sa apelam mai usor desenarea simbolului
make_text_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_text
	add esp, 16
endm

;macro care deseneaza o linie orizontala
line_horizontal macro x, y, len, color
local bucla_line
	pusha
	mov eax, y ; eax=y
	mov ebx, area_width
	mul ebx ; EAX = y * area_width
	add eax, x ; EAX = y * area_width + x
	shl eax, 2 ; EAX = (y * area_width + x)*4
	add eax, area
	mov ecx, len
bucla_line:
	mov dword ptr[eax], color
	add eax, 4
	loop bucla_line
	popa
endm

;macro care deseneaza o linie verticala
line_vertical macro x, y, len, color
local bucla_line
	pusha
	mov eax, y ; eax=y
	mov ebx, area_width
	mul ebx ; EAX = y * area_width
	add eax, x ; EAX = y * area_width + x
	shl eax, 2 ; EAX = (y * area_width + x)*4
	add eax, area
	mov ecx, len
bucla_line:
	mov dword ptr[eax], color
	add eax, 4*area_width
	loop bucla_line
	popa
endm

;macro care coloreaza o singura celula
color_celula macro x, y, color
local bucla_color_celula
	pusha
	mov esi, y
	inc esi
	mov edi, x
	inc edi
	mov ecx, esi
	add ecx, cel_size
	sub ecx, 1
	mov edx, cel_size
	dec edx
bucla_color_celula:
	line_horizontal edi, esi, cel_size - 1 , color ;0BE1813h-cod rosu 4EAFDDh-cod albastru
	inc esi
	cmp esi, ecx
	jne bucla_color_celula
	popa
endm

;macro ce coloreaza o celula in functie de coordonatele la care se da click si de  valoarea din matrix
color_matrix macro x, y
local compara_cu_0, final_color_celula
	pusha
	mov esi, 0
	mov edi, 0
	
	; calculam i-ul si j-ul in functie de coordonatele la care se da click
	mov eax, x  ; j = x / cel_size - 1
bucla_scadere:
	sub eax, cel_size
	inc esi ;pastram in esi de cate ori s-a efectuat scaderea
	cmp eax, cel_size
	jg bucla_scadere
	dec esi
	mov j, esi
	
	mov eax, y ; i = y / cel_size - 1
bucla_scadere_y:
	sub eax, cel_size
	inc edi ;pastram in edi de cate ori s-a efectuat scaderea
	cmp eax, cel_size
	jg bucla_scadere_y
	dec edi
	mov i, edi
	; afisare i
	; afisare j
	
	; pozitia din matrice = i * matrix_dim + j
	mov eax, i
	mov ebx, matrix_dim
	mul ebx
	mov ebx, j
	add eax, ebx
	mov esi, eax
	afisare esi
	
	;calculcam coordonatele celulei pe care trebuie sa o coloram
	; coord_x = (j+1) * cel_size
	; coord_y = (i+1) * cel_size
	inc i
	mov eax, i
	mov ebx, cel_size
	mul ebx
	mov coord_y, eax
	inc j
	mov eax, j
	mov ebx, cel_size
	mul ebx
	mov coord_x, eax
	; afisare coord_x
	; afisare coord_y
	
	;verificam daca am dat click in afara matricei
	cmp i, matrix_dim
	jg afisare_litere
	cmp j, matrix_dim
	jg afisare_litere
	
	; verificam valoarea din matrice la (i,j)
	; daca este egala cu 1, acolo avem vaporas si se va colora cu rosu
	; daca este egala cu 0, acolo avem apa si se va colora cu albastru
	cmp matrix[4 * esi], vaporas	
	jnz compara_cu_0
	color_celula coord_x, coord_y, ROSU
	dec vaporase_nedescoperite
	mov matrix[4 * esi], 2
	inc lovituri_succes
	jmp sari
	
compara_cu_0:
	cmp matrix[4 * esi], 0
	jne afisare_litere
	color_celula coord_x, coord_y, ALBASTRU
	mov matrix[4 * esi], 2
	inc ratari
	jmp afisare_litere
sari:
	popa
endm

;macro care genereaza un numar random
random_number macro nr
	pusha 
	rdtsc
	mov ebx, 10
	mov edx, 0
	div ebx
	mov nr, edx
	popa
endm

;verificam daca putem pune un vaporas pe o anumita linie
;ok=1 daca este libera, ok=0 altfel
linie_libera macro linie, i, dim 
local nu_merge, gata, verificare
	pusha
		;pozitia de start a vaporasului = linie * matrix_dim + i
		;pozitia de final a vaporasului = linie * matrix_dim + i + dim
		mov eax, linie
		mov edx, matrix_dim
		mul edx
		add eax, i
		mov edi, eax
		mov esi, eax
		add edi, dim
		inc edi
		;verificam daca vaporasul depaseste dimensiunea unei linii
		xor eax, eax
		mov eax, i
		add eax, dim
		dec eax
		cmp eax, matrix_dim
		jge nu_merge
		cmp matrix[4*esi], 1
		je nu_merge
		
		verificare: 
			cmp matrix[4*esi], 1
			je nu_merge
			inc esi
			cmp esi, edi
			jne verificare
		mov ok, 1
		jmp gata
	nu_merge:
		mov ok, 0
	gata:
	popa
endm

;verificam daca putem pune un vaporas pe o anumita coloana
;ok=1 daca este libera, ok=0 altfel
coloana_libera macro coloana,i, dim
local verificare2, nu_merge2, gata2
	pusha 
		; pozitia de start a vaporasului = i * matrix_dim + coloana
		; pozitia de final a vaporasului = ( i + dim ) * matrix_dim + coloana
		mov eax, i
		mov edx, matrix_dim
		mul edx
		add eax, coloana
		mov esi, eax
		xor eax, eax
		mov eax, i
		add eax, dim
		inc eax
		mov edx, matrix_dim
		mul edx
		add eax, coloana
		mov edi, eax
		;verificam daca vaporasul depaseste dimensiunea unei coloane
		xor eax, eax
		mov eax, i
		add eax, dim
		dec eax
		cmp eax, matrix_dim
		jge nu_merge2
		verificare2: 
			cmp matrix[4*esi], 1
			je nu_merge2
			add esi, matrix_dim
			cmp esi, edi
			jne verificare2
		mov ok, 1
		jmp gata2
	nu_merge2:
		mov ok, 0
	gata2:
	popa
endm

;punem un vaporas pe o anumita linie
pune1_linie macro linie, i, dim
local bucla_pune1
	pusha
		mov eax, linie
		mov edx, matrix_dim
		mul edx
		add eax, i
		mov edi, eax
		mov esi, eax
		add edi, dim
		bucla_pune1: 
			mov matrix[4*esi], 1
			inc esi
			cmp esi, edi
			jne bucla_pune1
	popa
endm

;punem un vaporas pe o anumita coloana
pune1_coloana macro coloana,i,dim
local bucla_pune
	pusha 
		mov eax, i
		mov edx, matrix_dim
		mul edx
		add eax, coloana
		mov esi, eax
		xor eax, eax
		mov eax, i
		add eax, dim
		mov edx, matrix_dim
		mul edx
		add eax, coloana
		mov edi, eax
		afisare esi
		afisare edi
		bucla_pune: 
			mov matrix[4*esi], 1
			add esi, matrix_dim
			afisare esi
			cmp esi, edi
			jne bucla_pune
	popa
endm

pune_vaporas macro linie, i, dim
local peste, verifica_coloana
	pusha
	mov ebx, i
	; afisare ebx
	mov ecx, linie
	; afisare ecx
		;verificam daca este libera linia
		linie_libera ecx, ebx, dim
		cmp ok, 1
		jne verifica_coloana
		pune1_linie ecx, ebx, dim
		inc nr_vaporase
		mov eax, vaporase_nedescoperite
		add eax, dim
		mov vaporase_nedescoperite, eax
		jmp peste
		
		;verificam daca este libera coloana
		verifica_coloana:
			coloana_libera ecx, ebx, dim
			cmp ok, 1
			jne peste
			pune1_coloana ecx, ebx, dim
			inc nr_vaporase
			mov eax, vaporase_nedescoperite
			add eax, dim
			mov vaporase_nedescoperite, eax
		peste:
	popa
endm

; functia de desenare - se apeleaza la fiecare click
; sau la fiecare interval de 200ms in care nu s-a dat click
; arg1 - evt (0 - initializare, 1 - click, 2 - s-a scurs intervalul fara click)
; arg2 - x
; arg3 - y
draw proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1]
	cmp eax, 1
	jz evt_click
	cmp eax, 2
	jz evt_timer ; nu s-a efectuat click pe nimic
	;mai jos e codul care intializeaza fereastra cu pixeli albi
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	push 255
	push area
	call memset
	add esp, 12
	jmp afisare_litere
	
evt_click:
	mov eax, [ebp+arg2]
	cmp eax, cel_size
	jl afisare_litere
	mov eax, [ebp+arg3]
	cmp eax, cel_size
	jl afisare_litere
	;coloram patratelul unde se da click
	color_matrix [ebp+arg2], [ebp+arg3]
	
evt_timer:
	 inc counter
	
afisare_litere:
	;afisam valoarea counter-ului curent (sute, zeci si unitati)
	mov ebx, 10
	mov eax, counter
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 30, 10
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 20, 10
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 10, 10
	
	cmp tabla_initializata, 0
	jne sari_peste
	;coloram toata tabla de joc cu roz daca nu a mai fost colorata inainte
	mov esi, cel_size + 1
	mov edi, cel_size + 1
bucla_color_tabla:
	line_horizontal cel_size + 1, edi, area_width-cel_size-450, ROZ ;32BE13h-cod verde 0F0D2DAh-cod roz
	inc edi
	inc esi
	cmp esi, area_height
	jne bucla_color_tabla
	mov tabla_initializata, 1
	
sari_peste:
	;afisam liniile 
	mov esi, 0
	mov edi, cel_size
bucla_afis_linii:
	line_horizontal 0, edi, area_width - 450, 0h
	add edi, cel_size
	inc esi
	cmp esi, nr_line
	jne bucla_afis_linii
	
	;afisam coloanele
	mov esi, 0
	mov edi, cel_size
bucla_afis_coloane:
	line_vertical edi, 0, area_height, 0h
	add edi, cel_size
	inc esi
	cmp esi, nr_cols
	jne bucla_afis_coloane
	
	;afisam coloana de litere
	mov edi, 0
	mov esi, cel_size + (cel_size - symbol_height)/2
bucla_afis_litere:
	make_text_macro array_litera[edi*4], area, (cel_size - symbol_width)/2, esi
	inc edi
	add esi, cel_size
	cmp edi, nr_line
	jne bucla_afis_litere
	
	;afisam coloana de cifre
	mov edi, 0
	mov esi, cel_size + (cel_size - symbol_width)/2
bucla_afis_cifre:
	make_text_macro array_cifra[edi*4], area, esi, (cel_size - symbol_height)/2
	inc edi
	add esi, cel_size
	cmp edi, nr_cols-2
	jne bucla_afis_cifre
	
	;afisare 10 in ultima coloana
	make_text_macro '1', area, 10*cel_size+(cel_size-2*symbol_width)/2, (cel_size - symbol_height)/2
	make_text_macro '0', area, 10*cel_size+(cel_size-2*symbol_width)/2+symbol_width, (cel_size - symbol_height)/2
	
	;afisarea partilor nedescoperite din vaporasele
	make_text_macro 'P', area, 570, 215
	make_text_macro 'A', area, 580, 215
	make_text_macro 'R', area, 590, 215
	make_text_macro 'T', area, 600, 215
	make_text_macro 'I', area, 610, 215
	
	make_text_macro ' ', area, 620, 215
	
	make_text_macro 'D', area, 630, 215
	make_text_macro 'E', area, 640, 215
	
	make_text_macro ' ', area, 650, 215
	
	make_text_macro 'V', area, 660, 215
	make_text_macro 'A', area, 670, 215
	make_text_macro 'P', area, 680, 215
	make_text_macro 'O', area, 690, 215
	make_text_macro 'R', area, 700, 215
	make_text_macro 'A', area, 710, 215
	make_text_macro 'S', area, 720, 215
	make_text_macro 'E', area, 730, 215
	
	make_text_macro ' ', area, 740, 215
	
	make_text_macro 'N', area, 750, 215
	make_text_macro 'E', area, 760, 215
	make_text_macro 'D', area, 770, 215
	make_text_macro 'E', area, 780, 215
	make_text_macro 'S', area, 790, 215
	make_text_macro 'C', area, 800, 215
	make_text_macro 'O', area, 810, 215
	make_text_macro 'P', area, 820, 215
	make_text_macro 'E', area, 830, 215
	make_text_macro 'R', area, 840, 215
	make_text_macro 'I', area, 850, 215
	make_text_macro 'T', area, 860, 215
	make_text_macro 'E', area, 870, 215
	
	make_text_macro ' ', area, 880, 215
	
	mov ebx, 10
	mov eax, vaporase_nedescoperite
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 910, 215
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 900, 215
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 890, 215
	
	;afisarea numarului de lovituri cu succes
	make_text_macro 'L', area, 570, 245
	make_text_macro 'O', area, 580, 245
	make_text_macro 'V', area, 590, 245
	make_text_macro 'I', area, 600, 245
	make_text_macro 'T', area, 610, 245
	make_text_macro 'U', area, 620, 245
	make_text_macro 'R', area, 630, 245
	make_text_macro 'I', area, 640, 245
	
	make_text_macro ' ', area, 650, 245
	
	make_text_macro 'C', area, 660, 245
	make_text_macro 'U', area, 670, 245
	
	make_text_macro ' ', area, 680, 245
	
	make_text_macro 'S', area, 690, 245
	make_text_macro 'U', area, 700, 245
	make_text_macro 'C', area, 710, 245
	make_text_macro 'C', area, 720, 245
	make_text_macro 'E', area, 730, 245
	make_text_macro 'S', area, 740, 245
	
	make_text_macro ' ', area, 750, 245
	
	mov ebx, 10
	mov eax, lovituri_succes
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 780, 245
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 770, 245
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 760, 245
	
	;afisam numarul ratarilor
	make_text_macro 'R', area, 570, 275
	make_text_macro 'A', area, 580, 275
	make_text_macro 'T', area, 590, 275
	make_text_macro 'A', area, 600, 275
	make_text_macro 'R', area, 610, 275
	make_text_macro 'I', area, 620, 275
	
	make_text_macro ' ', area, 630, 275
	
	mov ebx, 10
	mov eax, ratari
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 660, 275
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 650, 275
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 640, 275
	
final_draw:
	popa
	mov esp, ebp
	pop ebp
	ret
draw endp

start:

vaporas6:
	random_number linie
	afisare linie
	random_number i
	afisare i
	pune_vaporas linie, i, 6
	mov esi, nr_vaporase
	cmp esi, 2
	jne vaporas6

vaporas5:
	random_number linie
	afisare linie
	random_number i
	afisare i
	pune_vaporas linie, i, 5
	mov esi, nr_vaporase
	cmp esi, 4
	jne vaporas5
	
vaporas4:
	random_number linie
	afisare linie
	random_number i
	afisare i
	pune_vaporas linie, i, 4
	mov esi, nr_vaporase
	cmp esi, 6
	jne vaporas4
	
vaporas3:
	random_number linie
	afisare linie
	random_number i
	afisare i
	pune_vaporas linie, i, 3
	mov esi, nr_vaporase
	cmp esi, 8
	jne vaporas3
	
vaporas2:
	random_number linie
	afisare linie
	random_number i
	afisare i
	pune_vaporas linie, i,2
	mov esi, nr_vaporase
	cmp esi, 10
	jne vaporas2

	;alocam memorie pentru zona de desenat
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	call malloc
	add esp, 4
	mov area, eax
	;apelam functia de desenare a ferestrei
	; typedef void (*DrawFunc)(int evt, int x, int y);
	; void __cdecl BeginDrawing(const char *title, int width, int height, unsigned int *area, DrawFunc draw);
	push offset draw
	push area
	push area_height
	push area_width
	push offset window_title
	call BeginDrawing
	add esp, 20
	
	
	;terminarea programului
	push 0
	call exit
end start