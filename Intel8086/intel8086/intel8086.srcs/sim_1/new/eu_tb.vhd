library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity eu_tb is
end eu_tb;

architecture Behavioral of eu_tb is
    component eu
        Port (clk: in std_logic;
            reset : in std_logic;
            enable : in std_logic;
            instruction: in std_logic_vector(31 downto 0);
            aluOp: out std_logic_vector(2 downto 0);
            memDataWrite: out std_logic;
            memAdd: out std_logic;
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
            rd1: out std_logic_vector(15 downto 0);
            rd2: out std_logic_vector(15 downto 0);
            resetqueue: out std_logic;
            result: out std_logic_vector(15 downto 0);
            flags: out std_logic_vector(8 downto 0);
            nextip: out std_logic_vector(15 downto 0);
            next_state_debug : out std_logic_vector(2 downto 0)
             );
    end component;

    signal clk            : std_logic := '0';
    signal reset            : std_logic := '0';
    signal enable            : std_logic := '0';
    signal instruction    : std_logic_vector(31 downto 0) := (others => '0');
    signal aluOp          : std_logic_vector(2 downto 0);
    signal memDataWrite   : std_logic;
    signal memAdd         : std_logic;
    signal memWrite       : std_logic;
    signal loadQ          : std_logic;
    signal readQ          : std_logic;
    signal regWD          : std_logic_vector(1 downto 0);
    signal regWrite       : std_logic;
    signal operand2       : std_logic_vector(1 downto 0);
    signal jmp            : std_logic;
    signal je             : std_logic;
    signal jg             : std_logic;
    signal jne            : std_logic;
    signal reg1,next_state_debug          : std_logic_vector(2 downto 0);
    signal reg2           : std_logic_vector(2 downto 0);
    signal seg            : std_logic_vector(1 downto 0);
    signal imm_offset     : std_logic_vector(15 downto 0);
    signal d              : std_logic;
    signal w              : std_logic;
    signal rd1            : std_logic_vector(15 downto 0);
    signal rd2            : std_logic_vector(15 downto 0);
    signal resetqueue     : std_logic;
    signal result, nextip         : std_logic_vector(15 downto 0);
    signal flags          : std_logic_vector(8 downto 0);

    constant clk_period : time := 10 ns;
    type instruction_array is array (0 to 26) of std_logic_vector(31 downto 0);
    constant instructions : instruction_array := (
        0 => "10111000000000000000101000000000", -- mov ax, 10 -- 8B060000
        1 => "10111011000000000000010100000000", -- mov bx, 5 -- 8B1E0001
        2 => "10111001000000000000010000000000", -- mov cx, 4 -- B9000400
        3 => "10111010000000000000001000000000", -- mov dx, 2 -- BA000200
        4 => "00000001110000110000000000000000", -- add ax, bx -- 01C3000
        5 => "00101001110010100000000000000000", -- sub cx, dx -- 29CA0000
        6 => "10001001000001100000000000000010", -- mov result, ax -- 89060002
        7 => "00111001110010100000000000000000", -- cmp cx,dx -- 39CA0000
        8 => "10000001110000000000000000000000", -- noop -- 81C00000
        9 => "01110100000000000000101100000000", -- je equal_label -- 74000B00
        10 => "01110101000000000000110000000000", -- jne not_equal_label -- 75000C00
        11 => "10000001110110000000000000000101", -- equal_label: add bx, 5 -- 81D80005
        12 => "00100001110000010000000000000000", -- not equal_label: and ax, cx -- 21C10000
        13 => "10000001110111010000000000000011", -- sub bx,3 -- 81DD0003 
        14 => "00001001110010100000000000000000", -- or cx,dx -- 09CA0000
        15 => "11101001000000000001001000000000", -- jmp continue_label -- E9001200
        16 => "10111000000000000000000000000000", -- reset_label: mov ax, 0 -- B8000000
        17 => "11101001000000000001101100000000", -- jmp end_label -- E9001B00
        18 => "00110001110000110000000000000000", -- continue_label: xor ax, bx -- 31C30000
        19 => "10000000111111000000000000000101", -- and bh, 5 -- 80FC0005
        20 => "10000000110000010000000000000011", -- or al, 3 -- 80C10003
        21 => "10000001110111100000000000000011", -- xor bx, 2 -- 81DE0002
        22 => "00000001000001100000000000000000", -- add ax, value1 -- 01060000
        23 => "10000001110000000000000000000000", -- noop -- 81C00000
        24 => "10000001110001110000000000000001",-- cmp ax,1 -- 81C70001
        25 => "10000001110000000000000000000000", -- noop -- 81C00000
        26 => "01111111000000000001000000000000" -- jg reset_label -- 7F001000
    );
begin
    uut: eu
        Port map (
            clk             => clk,
            reset => reset,
            enable => enable,
            instruction     => instruction,
            aluOp           => aluOp,
            memDataWrite    => memDataWrite,
            memAdd          => memAdd,
            memWrite        => memWrite,
            loadQ           => loadQ,
            readQ           => readQ,
            regWD           => regWD,
            regWrite        => regWrite,
            operand2        => operand2,
            jmp             => jmp,
            je              => je,
            jg              => jg,
            jne             => jne,
            reg1            => reg1,
            reg2            => reg2,
            seg             => seg,
            imm_offset      => imm_offset,
            d               => d,
            w               => w,
            rd1             => rd1,
            rd2             => rd2,
            resetqueue      => resetqueue,
            result          => result,
            flags           => flags,
            nextip          => nextip,
            next_state_debug => next_state_debug
        );

    clk_process : process
    begin
        while true loop
            clk <= '0';
            wait for clk_period / 2;
            clk <= '1';
            wait for clk_period / 2;
        end loop;
    end process;

    stimulus_process: process
    begin
        enable <= '1'; 
         for i in 0 to 26 loop
            instruction <= instructions(i);
            wait for 5*clk_period; 
        end loop;

        wait;
    end process;

end Behavioral;
