library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity tb_InstructionQueue is
end tb_InstructionQueue;

architecture Behavioral of tb_InstructionQueue is

    component InstructionQueue
        Port (
            clk            : in  std_logic;
            reset          : in  std_logic;
            loadQ          : in  std_logic;
            read_enable    : in  std_logic;
            instruction    : in  std_logic_vector(31 downto 0);
            queueFull      : out std_logic;
            queueEmpty     : out std_logic;
            instructionOut : out std_logic_vector(31 downto 0)
        );
    end component;

    signal clk            : std_logic := '0';
    signal reset          : std_logic := '0';
    signal loadQ          : std_logic := '0';
    signal read_enable    : std_logic := '0';
    signal instruction    : std_logic_vector(31 downto 0) := (others => '0');
    signal queueFull      : std_logic;
    signal queueEmpty     : std_logic;
    signal instructionOut : std_logic_vector(31 downto 0);

    constant clk_period : time := 10 ns;

begin

    uut: InstructionQueue
        Port map (
            clk => clk,
            reset => reset,
            loadQ => loadQ,
            read_enable => read_enable,
            instruction => instruction,
            queueFull => queueFull,
            queueEmpty => queueEmpty,
            instructionOut => instructionOut
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
        -- Reset the queue
        reset <= '1';
        wait for clk_period;
        reset <= '0';

        loadQ <= '1';
        instruction <= x"00000001";
        wait for clk_period;
        instruction <= x"00000002";
        wait for clk_period;
        instruction <= x"00000003";
        wait for clk_period;
        instruction <= x"00000004";
        wait for clk_period;
        instruction <= x"00000005";
        wait for clk_period;
        instruction <= x"00000006";
        wait for clk_period;
        loadQ <= '0';

        -- Check queueFull signal
        wait for clk_period;

        -- Read instructions from the queue
        for i in 0 to 5 loop
            read_enable <= '1';
            wait for clk_period;
            read_enable <= '0';
            wait for clk_period;
        end loop;

        -- Check queueEmpty signal
        wait for clk_period;

        -- Finish simulation
        wait for 100 ns;
        wait;
    end process;

end Behavioral;
