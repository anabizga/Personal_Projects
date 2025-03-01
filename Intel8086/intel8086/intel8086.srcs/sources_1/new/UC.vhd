----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 27.11.2024 12:19:49
-- Design Name: 
-- Module Name: UC - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity UC is
Port ( clk: in std_logic;
    reset: in std_logic;
    enable: in std_logic;
    instruction: in std_logic_vector(31 downto 0);
    aluOp: out std_logic_vector(2 downto 0);
    memDataWrite: out std_logic;
    memAdd: out std_logic;
    enableNextIp: out std_logic;
    enableRead: out std_logic;
    enableFlags: out std_logic;
    memWrite: out std_logic;
    loadQ: out std_logic;
    readQ: out std_logic;
    regWD: out std_logic_vector(1 downto 0);
    regWrite: out std_logic;
    operand2: out std_logic_vector(1 downto 0);
    jmp: out std_logic;
    je: out std_logic;
    jg: out std_logic;
    jne: out std_logic;
    reg1: out std_logic_vector(2 downto 0);
    reg2: out std_logic_vector(2 downto 0);
    seg: out std_logic_vector(1 downto 0);
    imm_offset: out std_logic_vector(15 downto 0);
    d: out std_logic;
    w: out std_logic;
    next_state_debug : out std_logic_vector(2 downto 0)
);
end UC;

architecture Behavioral of UC is
type state_type is (FETCH, DECODE, MEMORY_READ, EXECUTE, MEMORY_WRITE, WRITE_BACK);
signal current_state, next_state : state_type;
begin

    process(clk, reset)
    begin
        if reset = '1' then
            current_state <= FETCH;
        elsif rising_edge(clk) and enable = '1' then
            current_state <= next_state;
        end if;
    end process;
    
    process(current_state, instruction)
    begin
        memDataWrite <= '0';
        memAdd <= '0';
        memWrite <= '0';
        enablenextIp <= '0';
        enableRead <= '0';
        loadQ <= '0';
        readQ <= '0';
        enableflags <= '0';
        regWrite <= '0';
        regWD <= "00";
        operand2 <= "00";
        jmp <= '0';
        je <= '0';
        jne <= '0';
        jg <= '0';
        reg1 <= "000";
        reg2 <= "000";
        seg <= "00";
        imm_offset <= x"0000";
        d <= '0';
        w <= '0';
        
        case current_state is
            when FETCH => 
                aluOp <= "000"; --default add
                memDataWrite <= '0';
                memAdd <= '0';
                memWrite <= '0';
                enablenextIp <= '1';
                enableRead <= '1';
                loadQ <= '1';
                readQ <= '0';
                regWrite <= '0';
                regWD <= "00";
                operand2 <= "00";
                enableflags <= '0';
                jmp <= '0';
                je <= '0';
                jne <= '0';
                jg <= '0';
                reg1 <= "000";
                reg2 <= "000";
                seg <= "00";
                imm_offset <= x"0000";
                d <= '0';
                w <= '0';
                next_state <= DECODE;
                
            when DECODE =>
                aluOp <= "000"; --default add
                memDataWrite <= '0';
                memAdd <= '0';
                memWrite <= '0';
                enablenextIp <= '0';
                enableRead <= '0';
                loadQ <= '0';
                enableflags <= '0';
                readQ <= '1';
                regWrite <= '0';
                regWD <= "00";
                operand2 <= "00";
                jmp <= '0';
                je <= '0';
                jne <= '0';
                jg <= '0';
                reg1 <= "000";
                reg2 <= "000";
                seg <= "00";
                imm_offset <= x"0000";
                d <= '0';
                w <= '0';
                case instruction(31 downto 26) is
                    when "100010" => -- instructiune de tip mov
                    if instruction(23 downto 22) = "11" then -- instructiunea mov reg, reg
                        next_state <= EXECUTE;
                    elsif instruction(23 downto 22) = "00" then -- instructiuni de tip mov cu adresare directa
                        next_state <= MEMORY_READ;
                    end if;
            
                    when "000000" => -- instructiune add
                        if instruction(23 downto 22) = "11" then -- instructiune add reg, reg
                            next_state <= EXECUTE;
                        elsif instruction(23 downto 22) = "00" then -- instructiune add reg si memorie
                            next_state <= MEMORY_READ;
                        end if;
                
                    when "001010"  => -- instructiune sub reg, reg        
                        next_state <= EXECUTE;
                
                    when "001000"  => -- instructiune and reg, reg        
                        next_state <= EXECUTE;
        
                    when "000010"  => -- instructiune or reg, reg        
                        next_state <= EXECUTE;
        
                    when "001100"  => -- instructiune xor reg, reg        
                        next_state <= EXECUTE;
        
                    when "001110"  => -- instructiune cmp reg, reg        
                        next_state <= EXECUTE;
                
                    when "100000" => -- instructiuni cu imediat
                        next_state <= EXECUTE;
                
                    when others => 
                        case instruction(31 downto 24) is 
                        when "11101001" => --jump address
                            next_state <= EXECUTE;
                        when "01110100" => -- je address
                            next_state <= EXECUTE;
                        when "01110101" => -- jne address
                            next_state <= EXECUTE;
                        when "01111111" => --jg address
                            next_state <= EXECUTE;
                        when others => if instruction(31 downto 27) = "10111" then -- mov reg, imm
                                        next_state <= EXECUTE;
                                       end if; 
                        end case;
                    end case;
                    
            when MEMORY_READ => 
                aluOp <= "000"; --default add
                memWrite <= '0';
                enablenextIp <= '0';
                loadQ <= '0';
                readQ <= '0';
                regWrite <= '0';
                enableRead <= '1';
                regWD <= "00";
                operand2 <= "00";
                jmp <= '0';
                je <= '0';
                jne <= '0';
                jg <= '0';
                reg1 <= "000";
                enableflags <= '0';
                reg2 <= "000";
                d <= '0';
                w <= '0';
                memDataWrite <= '1';
                memAdd <= '1';
                imm_offset <= instruction(15 downto 0);
                seg <= "01"; --DS
                next_state <= EXECUTE;
                    
            when EXECUTE =>
                memDataWrite <= '0';
                memAdd <= '0';
                memWrite <= '0';
                enablenextIp <= '0';
                loadQ <= '0';
                readQ <= '0';
                regWrite <= '0';
                enableRead <= '0';
                enableflags <= '1';
                regWD <= "00";
                operand2 <= "00";
                jmp <= '0';
                je <= '0';
                jne <= '0';
                jg <= '0';
                reg1 <= "000";
                reg2 <= "000";
                seg <= "00";
                imm_offset <= x"0000";
                d <= '0';
                w <= '0';    
                     
                case instruction(31 downto 26) is
                    when "100010" => -- instructiune de tip mov
                        if instruction(23 downto 22) = "11" then -- instructiunea mov reg, reg
                            jmp <= '0';
                            je <= '0';
                            jne <= '0';
                            jg <= '0';
                            reg1 <= instruction(21 downto 19);
                            reg2 <= instruction(18 downto 16);
                            d <= instruction(25);
                            w <= instruction(24);
                            next_state <= WRITE_BACK;
                        elsif instruction(23 downto 22) = "00" then -- instructiuni de tip mov cu adresare directa
                            if instruction(25) = '0' then -- instructiune mov [mem], reg (datele sunt mutate din registru in memorie)
                                jmp <= '0';
                                je <= '0';
                                jne <= '0';
                                jg <= '0';
                                reg1 <= instruction(21 downto 19);
                                d <= instruction(25);
                                w <= instruction(24);
                                next_state <= MEMORY_WRITE;
                            else -- instructiune mov reg, [mem] (datele sunt mutate din memorie in registru)
                                jmp <= '0';
                                je <= '0';
                                jne <= '0';
                                jg <= '0';
                                reg1 <= instruction(21 downto 19);
                                d <= instruction(25);
                                w <= instruction(24);
                                next_state <= WRITE_BACK;
                            end if;
                        end if;
                    
                       when "000000" => -- instructiune add
                            if instruction(23 downto 22) = "11" then -- instructiune add reg, reg
                            aluOp <= "000"; -- +
                            operand2 <= "00"; -- al doilea registru
                            jmp <= '0';
                            je <= '0';
                            jne <= '0';
                            jg <= '0';
                            reg1 <= instruction(21 downto 19);
                            reg2 <= instruction(18 downto 16);
                            d <= instruction(25);
                            w <= instruction(24);
                            next_state <= WRITE_BACK;
                        elsif instruction(23 downto 22) = "00" then -- instructiune add reg si memorie
                            if instruction(25) = '0' then -- instructiune add reg, mem (rezultatul se va pune in registru)
                                aluOp <= "000"; -- +
                                operand2 <= "10"; -- datele din memorie
                                jmp <= '0';
                                je <= '0';
                                jne <= '0';
                                jg <= '0';
                                reg1 <= instruction(21 downto 19);
                                d <= instruction(25);
                                w <= instruction(24);
                                next_state <= WRITE_BACK;
                            else -- instructiune add mem, reg (rezultatul se va pune in memorie)
                                aluOp <= "000"; -- +
                                jmp <= '0';
                                je <= '0';
                                jne <= '0';
                                jg <= '0';
                                reg1 <= instruction(21 downto 19);
                                d <= instruction(25);
                                w <= instruction(24);
                                next_state <= MEMORY_WRITE;
                            end if;
                        end if;
                        
                    when "001010"  => -- instructiune sub reg, reg        
                        aluOp <= "001"; -- -
                        operand2 <= "00"; -- al doilea registru
                        jmp <= '0';
                        je <= '0';
                        jne <= '0';
                        jg <= '0';
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                        next_state <= WRITE_BACK;
                        
                    when "001000"  => -- instructiune and reg, reg        
                        aluOp <= "010"; -- &
                        operand2 <= "00"; -- al doilea registru
                        jmp <= '0';
                        je <= '0';
                        jne <= '0';
                        jg <= '0';
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                        next_state <= WRITE_BACK;
                
                    when "000010"  => -- instructiune or reg, reg        
                        aluOp <= "011"; -- |
                        operand2 <= "00"; -- al doilea registru
                        jmp <= '0';
                        je <= '0';
                        jne <= '0';
                        jg <= '0';
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                        next_state <= WRITE_BACK;
                
                    when "001100"  => -- instructiune xor reg, reg        
                        aluOp <= "100"; -- ^
                        operand2 <= "00"; -- al doilea registru
                        jmp <= '0';
                        je <= '0';
                        jne <= '0';
                        jg <= '0';
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                        next_state <= WRITE_BACK;
                
                    when "001110"  => -- instructiune cmp reg, reg        
                        aluOp <= "001"; -- -
                        operand2 <= "00"; -- al doilea registru
                        jmp <= '0';
                        je <= '0';
                        jne <= '0';
                        jg <= '0';
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                        next_state <= WRITE_BACK;
                        
                    when "100000" => -- instructiuni cu imediat
                        operand2 <= "01"; -- imediatul
                        jmp <= '0';
                        je <= '0';
                        jne <= '0';
                        jg <= '0';
                        reg1 <= instruction(21 downto 19);
                        d <= instruction(25);
                        w <= instruction(24);
                        imm_offset <= instruction(15 downto 0);
                        next_state <= WRITE_BACK;
                    
                        case instruction(18 downto 16) is
                            when "000" => aluOp <= "000"; -- +
                            when "101" => aluOp <= "001"; -- -
                            when "100" => aluOp <= "010"; -- &
                            when "001" => aluOp <= "011"; -- |
                            when "110" => aluOp <= "100"; -- ^
                            when "111" => aluOp <= "001"; -- -
                            when others => aluOp <= "000"; -- +
                        end case;
                        
                    when others => 
                        case instruction(31 downto 24) is 
                            when "11101001" => --jump address
                                jmp <= '1';
                                je <= '0';
                                jne <= '0';
                                jg <= '0';
                                imm_offset <= instruction(23 downto 8);
                                next_state <= WRITE_BACK;
                            when "01110100" => -- je address
                                jmp <= '0';
                                je <= '1';
                                jne <= '0';
                                jg <= '0';
                                imm_offset <= instruction(23 downto 8);
                                next_state <= WRITE_BACK;
                            when "01110101" => -- jne address
                                jmp <= '0';
                                je <= '0';
                                jne <= '1';
                                jg <= '0';
                                imm_offset <= instruction(23 downto 8);
                                next_state <= WRITE_BACK;
                            when "01111111" => --jg address
                                jmp <= '0';
                                je <= '0';
                                jne <= '0';
                                jg <= '1';
                                imm_offset <= instruction(23 downto 8);
                                next_state <= WRITE_BACK;
                            when others => if instruction(31 downto 27) = "10111" then -- mov reg, imm
                                            jmp <= '0';
                                            je <= '0';
                                            jne <= '0';
                                            jg <= '0';
                                            w <= '1';
                                            reg1 <= instruction(26 downto 24);
                                            imm_offset <= instruction(23 downto 8);
                                            next_state <= WRITE_BACK;
                                            end if; 
                    end case;
                end case;
                
            when MEMORY_WRITE =>   
                memDataWrite <= '1';
                memAdd <= '1';
                memWrite <= '1';
                imm_offset <= instruction(15 downto 0);
                seg <= "01"; --DS
                enableflags <= '0';
                next_state <= FETCH;
                    
            when WRITE_BACK => 
                next_state <= FETCH;
                enableflags <= '0';
                case instruction(31 downto 26) is
                    when "100010" => -- instructiune de tip mov
                        if instruction(23 downto 22) = "11" then -- instructiunea mov reg, reg
                            regWrite <= '1';
                            regWD <= "11"; -- registrul 2
                            reg1 <= instruction(21 downto 19);
                            reg2 <= instruction(18 downto 16);
                            d <= instruction(25);
                            w <= instruction(24);
                        elsif instruction(23 downto 22) = "00" then -- instructiuni de tip mov cu adresare directa
                            if instruction(25) = '0' then -- instructiune mov [mem], reg (datele sunt mutate din registru in memorie)
                                regWrite <= '0';
                            else -- instructiune mov reg, [mem] (datele sunt mutate din memorie in registru)
                                regWrite <= '1';
                                regWD <= "00"; -- valoarea din memorie
                                reg1 <= instruction(21 downto 19);
                                d <= instruction(25);
                                w <= instruction(24);
                                imm_offset <= instruction(15 downto 0);
                                seg <= "01"; --DS
                            end if;
                        end if;
                    
                    when "000000" => -- instructiune add
                        if instruction(23 downto 22) = "11" then -- instructiune add reg, reg
                            regWrite <= '1';
                            regWD <= "10"; -- rezultatul ALU
                            reg1 <= instruction(21 downto 19);
                            reg2 <= instruction(18 downto 16);
                            d <= instruction(25);
                            w <= instruction(24);
                        elsif instruction(23 downto 22) = "00" then -- instructiune add reg si memorie
                            if instruction(25) = '0' then -- instructiune add reg, mem (rezultatul se va pune in registru)
                                regWrite <= '1';
                                regWD <= "10"; -- rezultatul din ALU
                                operand2 <= "10"; -- datele din memorie
                                reg1 <= instruction(21 downto 19);
                                d <= instruction(25);
                                w <= instruction(24);
                                imm_offset <= instruction(15 downto 0);
                                seg <= "01"; --DS
                            else -- instructiune add mem, reg (rezultatul se va pune in memorie)
                                regWrite <= '0';
                            end if;
                        end if;
                        
                    when "001010"  => -- instructiune sub reg, reg        
                        regWrite <= '1';
                        regWD <= "10"; -- rezultatul ALU
                        operand2 <= "00"; -- al doilea registru
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                        
                    when "001000"  => -- instructiune and reg, reg        
                        regWrite <= '1';
                        regWD <= "10"; -- rezultatul ALU
                        operand2 <= "00"; -- al doilea registru
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                
                    when "000010"  => -- instructiune or reg, reg        
                        regWrite <= '1';
                        regWD <= "10"; -- rezultatul ALU
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                
                    when "001100"  => -- instructiune xor reg, reg        
                        regWrite <= '1';
                        regWD <= "10"; -- rezultatul ALU
                        reg1 <= instruction(21 downto 19);
                        reg2 <= instruction(18 downto 16);
                        d <= instruction(25);
                        w <= instruction(24);
                
                    when "001110"  => -- instructiune cmp reg, reg        
                        regWrite <= '0';
                        
                    when "100000" => -- instructiuni cu imediat
                        regWrite <= '1';
                        regWD <= "10"; -- rezultatul ALU
                        reg1 <= instruction(21 downto 19);
                        d <= instruction(25);
                        w <= instruction(24);
                        imm_offset <= instruction(15 downto 0);
                    
                        case instruction(18 downto 16) is
                            when "000" => aluOp <= "000"; -- +
                            when "101" => aluOp <= "001"; -- -
                            when "100" => aluOp <= "010"; -- &
                            when "001" => aluOp <= "011"; -- |
                            when "110" => aluOp <= "100"; -- ^
                            when "111" => aluOp <= "001"; -- -
                                          regWrite <= '0';
                            when others => aluOp <= "000"; -- +
                                           regWrite <= '0';
                        end case;
                        
                    when others => 
                        case instruction(31 downto 24) is 
                            when "11101001" => --jump address
                                regWrite <= '0';
                            when "01110100" => -- je address
                                regWrite <= '0';
                            when "01110101" => -- jne address
                                regWrite <= '0';
                            when "01111111" => --jg address
                                regWrite <= '0';
                            when others => if instruction(31 downto 27) = "10111" then -- mov reg, imm
                                                regWrite <= '1';
                                                regWD <= "01"; -- se srie in registru immediatul 
                                                w <= '1';
                                                reg1 <= instruction(26 downto 24);
                                                imm_offset <= instruction(23 downto 8);
                                            end if; 
                        end case;
                end case;
                
            end case;
            
            case next_state is
                when FETCH        => next_state_debug <= "001";
                when DECODE       => next_state_debug <= "010";
                when MEMORY_READ  => next_state_debug <= "011";
                when EXECUTE      => next_state_debug <= "100";
                when MEMORY_WRITE => next_state_debug <= "101";
                when WRITE_BACK   => next_state_debug <= "110";
                when others       => next_state_debug <= "000";
            end case;
    end process;

end Behavioral;
