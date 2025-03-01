library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity InstructionDataFetch is
    Port (
        clk         : in  std_logic;
        ip:           in std_logic_vector(15 downto 0);
        en          : in  std_logic;
        codsegment  : in  std_logic_vector(15 downto 0);
        memAddress  : in  std_logic_vector(15 downto 0);
        wd          : in  std_logic_vector(15 downto 0);
        memWrite    : in  std_logic;
        memAdd      : in  std_logic;
        instruction : out std_logic_vector(31 downto 0);
        data        : out std_logic_vector(15 downto 0)
    );
end InstructionDataFetch;

architecture Behavioral of InstructionDataFetch is
    type mem_array is array(0 to 65535) of std_logic_vector(31 downto 0);
    signal memory : mem_array := (
        0 => "10001011000001100000000000000000", -- mov ax, value1 -- 8B060000
        1 => "10001011000111100000000000000001", -- mov bx, value2 -- 8B1E0001
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
        21 => "10000001110111100000000000000010", -- xor bx, 2 -- 81DE0002
        22 => "00000001000001100000000000000000", -- add ax, value1 -- 01060000
        23 => "10000001110000000000000000000000", -- noop -- 81C00000
        24 => "10000001110001110000000000000001",-- cmp ax,1 -- 81C70001
        25 => "10000001110000000000000000000000", -- noop -- 81C00000
        26 => "01111111000000000001000000000000", -- jg reset_label -- 7F001000
        27 => "00000000000000000000000000000000", -- end_label: -- 00000000
        64 => "00000000000000000000000000001010", -- value1=10 -- 0000000A
        65 => "00000000000000000000000000000101", -- value2=5 -- 00000005
        66 => "00000000000000000000000000000000", -- result -- 00000000
        others => X"00000000"
    );

    signal address, codAddress : std_logic_vector(15 downto 0) := x"0000";
    signal rd                  : std_logic_vector(31 downto 0) := x"00000000";
    signal instruction_reg     : std_logic_vector(31 downto 0) := x"00000000";
    signal data_reg, ip_aux            : std_logic_vector(15 downto 0) := x"0000";

begin

    codAddress <= ip + codsegment;
    address <= codAddress when memAdd = '0' else memAddress;

    process(clk)
    begin
    if rising_edge(clk) then
            if memWrite = '1' then
                memory(conv_integer(address)) <= "0000000000000000" & wd;
            elsif en = '1' then 
                if memAdd = '0' then 
                    instruction_reg <= memory(conv_integer(address));
                else data_reg <= memory(conv_integer(address))(15 downto 0);
                end if;
            end if;
    end if;
    end process;

    instruction <= instruction_reg;
    data <= data_reg;

end Behavioral;
