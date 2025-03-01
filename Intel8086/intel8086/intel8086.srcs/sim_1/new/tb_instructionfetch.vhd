library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity tb_instructionfetch is
end tb_instructionfetch;

architecture Behavioral of tb_instructionfetch is
    component InstructionDataFetch
        Port (
            clk         : in  std_logic;
            ip          : in  std_logic_vector(15 downto 0);
            en          : in  std_logic;
            codsegment  : in  std_logic_vector(15 downto 0);
            memAddress  : in  std_logic_vector(15 downto 0);
            wd          : in  std_logic_vector(15 downto 0);
            memWrite    : in  std_logic;
            memAdd      : in  std_logic;
            instruction : out std_logic_vector(31 downto 0);
            data        : out std_logic_vector(15 downto 0)
        );
    end component;

    signal clk         : std_logic := '0';
    signal ip          : std_logic_vector(15 downto 0) := x"0000";
    signal en          : std_logic := '0';
    signal codsegment  : std_logic_vector(15 downto 0) := x"0000";
    signal memAddress  : std_logic_vector(15 downto 0) := x"0000";
    signal wd          : std_logic_vector(15 downto 0) := x"0000";
    signal memWrite    : std_logic := '0';
    signal memAdd      : std_logic := '0';
    signal instruction : std_logic_vector(31 downto 0);
    signal data        : std_logic_vector(15 downto 0);

    constant clk_period : time := 10 ns;

begin

    uut: InstructionDataFetch
        Port map (
            clk => clk,
            ip => ip,
            en => en,
            codsegment => codsegment,
            memAddress => memAddress,
            wd => wd,
            memWrite => memWrite,
            memAdd => memAdd,
            instruction => instruction,
            data => data
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

    stim_proc: process
    begin
        -- citim prima instructiune
        en <= '1';
        memWrite <= '0';
        memAdd <= '0';
        ip <= x"0000";
        codsegment <= x"0000";
        memAddress <= x"0000";
        wd <= x"0000";
        wait for clk_period;

        -- citim o valoare din memorie
        en <= '1';
        memWrite <= '0';
        memAdd <= '1';
        ip <= x"0000";
        codsegment <= x"0040";
        memAddress <= x"0000";
        wd <= x"0000";
        wait for clk_period;
        
        -- scriem o valoare in memorie
        en <= '0';
        memWrite <= '1';
        memAdd <= '1';
        ip <= x"0000";
        codsegment <= x"0040";
        memAddress <= x"0000";
        wd <= x"1234";
        wait for clk_period;
        
        -- citim valoarea scrisa
        en <= '1';
        memWrite <= '0';
        memAdd <= '1';
        ip <= x"0000";
        codsegment <= x"0040";
        memAddress <= x"0000";
        wd <= x"0000";
        wait for clk_period;
        
        wait;
    end process;

end Behavioral;
