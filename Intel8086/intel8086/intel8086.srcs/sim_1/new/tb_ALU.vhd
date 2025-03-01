library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity tb_ALU is
end tb_ALU;

architecture Behavioral of tb_ALU is

    component ALU
        Port (
            clk          : in  std_logic;
            enable       : in  std_logic;
            reset        : in  std_logic;
            rd1          : in  std_logic_vector(15 downto 0);
            rd2          : in  std_logic_vector(15 downto 0);
            imm          : in  std_logic_vector(15 downto 0);
            memData      : in  std_logic_vector(15 downto 0);
            operand2     : in  std_logic_vector(1 downto 0);
            aluOp        : in  std_logic_vector(2 downto 0);
            memDataWrite : in  std_logic;
            dir          : in  std_logic;
            result       : out std_logic_vector(15 downto 0);
            flags        : out std_logic_vector(8 downto 0);
            wdMem        : out std_logic_vector(15 downto 0)
        );
    end component;

    -- Signals
    signal clk          : std_logic := '0';
    signal enable       : std_logic := '0';
    signal reset        : std_logic := '0';
    signal rd1          : std_logic_vector(15 downto 0) := (others => '0');
    signal rd2          : std_logic_vector(15 downto 0) := (others => '0');
    signal imm          : std_logic_vector(15 downto 0) := (others => '0');
    signal memData      : std_logic_vector(15 downto 0) := (others => '0');
    signal operand2     : std_logic_vector(1 downto 0) := "00";
    signal aluOp        : std_logic_vector(2 downto 0) := "000";
    signal memDataWrite : std_logic := '0';
    signal dir          : std_logic := '0';
    signal result       : std_logic_vector(15 downto 0);
    signal flags        : std_logic_vector(8 downto 0);
    signal wdMem        : std_logic_vector(15 downto 0);

    constant clk_period : time := 10 ns;

begin

    uut: ALU
        Port map (
            clk          => clk,
            enable       => enable,
            reset        => reset,
            rd1          => rd1,
            rd2          => rd2,
            imm          => imm,
            memData      => memData,
            operand2     => operand2,
            aluOp        => aluOp,
            memDataWrite => memDataWrite,
            dir          => dir,
            result       => result,
            flags        => flags,
            wdMem        => wdMem
        );

    -- Clock generation
    clk_process : process
    begin
        while true loop
            clk <= '0';
            wait for clk_period / 2;
            clk <= '1';
            wait for clk_period / 2;
        end loop;
    end process;

    -- Stimulus process
    stim_proc: process
    begin
        -- Reset ALU
        reset <= '1';
        wait for clk_period;
        reset <= '0';

        -- Test Case 1: ADD operation (A + B)
        enable <= '1';
        rd1 <= x"0005";
        rd2 <= x"0003";
        aluOp <= "000";  -- ADD
        operand2 <= "00";  -- Select rd2
        wait for clk_period;

        -- Test Case 2: SUB operation (A - B)
        dir <= '0';
        aluOp <= "001";  -- SUB
        wait for clk_period;

        -- Test Case 3: AND operation (A AND B)
        aluOp <= "010";  -- AND
        wait for clk_period;

        -- Test Case 4: OR operation (A OR B)
        aluOp <= "011";  -- OR
        wait for clk_period;

        -- Test Case 5: XOR operation (A XOR B)
        aluOp <= "100";  -- XOR
        wait for clk_period;

        -- Test Case 6: Test imm as operand (A + imm)
        operand2 <= "01";  -- Select imm
        imm <= x"0002";
        aluOp <= "000";  -- ADD
        wait for clk_period;

        -- Test Case 7: Test memData as operand (A + memData)
        operand2 <= "11";  -- Select memData
        memData <= x"0004";
        aluOp <= "000";  -- ADD
        wait for clk_period;

        -- End of simulation
        wait;
    end process;

end Behavioral;
