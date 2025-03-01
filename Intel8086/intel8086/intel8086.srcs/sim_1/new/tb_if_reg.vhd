library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity topLevel_tb is
end topLevel_tb;

architecture Behavioral of topLevel_tb is
    component topLevel
        Port (
            clk : in STD_LOGIC;
            reset : in STD_LOGIC;
            enable : in STD_LOGIC;
            enablenextip: out std_logic;
            instruction : out std_logic_vector(31 downto 0);
            nextIp : out std_logic_vector(15 downto 0);
            data : out std_logic_vector(15 downto 0);
            memAddress : out std_logic_vector(15 downto 0);
            segmentValue : out std_logic_vector(15 downto 0);
            wd : out std_logic_vector(15 downto 0);
            instructionOut : out std_logic_vector(31 downto 0);
            queueFull : out std_logic;
            queueEmpty : out std_logic;
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
            enableRead: out std_logic;
            next_state_debug : out std_logic_vector(2 downto 0);
            rd1: out std_logic_vector(15 downto 0);
            rd2: out std_logic_vector(15 downto 0);
            resetqueue: out std_logic;
            result: out std_logic_vector(15 downto 0);
            flags: out std_logic_vector(8 downto 0)
            );
    end component;
    signal clk : std_logic := '0';
    signal reset : std_logic := '0';
    signal enable, enablenextip : std_logic := '0';

    signal instruction : std_logic_vector(31 downto 0);
    signal nextIp : std_logic_vector(15 downto 0);
    signal data : std_logic_vector(15 downto 0);
    signal memAddress : std_logic_vector(15 downto 0);
    signal segmentValue : std_logic_vector(15 downto 0);
    signal wd : std_logic_vector(15 downto 0);
    signal instructionOut : std_logic_vector(31 downto 0);
    signal queueFull : std_logic;
    signal queueEmpty : std_logic;
    signal aluOp: std_logic_vector(2 downto 0);
    signal memDataWrite: std_logic;
    signal memAdd: std_logic;
    signal memWrite, enableRead: std_logic;
    signal loadQ: std_logic;
    signal readQ: std_logic;
    signal regWD: std_logic_vector(1 downto 0);
    signal regWrite: std_logic;
    signal operand2: std_logic_vector(1 downto 0);
    signal jmp: std_logic;
    signal je: std_logic;
    signal jg: std_logic;
    signal jne: std_logic;
    signal reg1: std_logic_vector(2 downto 0);
    signal reg2,next_state_debug : std_logic_vector(2 downto 0);
    signal seg: std_logic_vector(1 downto 0);
    signal imm_offset: std_logic_vector(15 downto 0);
    signal d: std_logic;
    signal w: std_logic;
    signal rd1:  std_logic_vector(15 downto 0):=x"0000";
    signal rd2:  std_logic_vector(15 downto 0):=x"0000";
    signal resetqueue:  std_logic:='0';
    signal result:  std_logic_vector(15 downto 0):=x"0000";
    signal flags:  std_logic_vector(8 downto 0):="000000000";

begin
    uut: topLevel
        Port map (
            clk => clk,
            reset => reset,
            enable => enable,
            enablenextip => enablenextip,
            instruction => instruction,
            nextIp => nextIp,
            data => data,
            memAddress => memAddress,
            segmentValue => segmentValue,
            wd => wd,
            instructionOut => instructionOut,
            queueFull => queueFull,
            queueEmpty => queueEmpty,
            aluOp => aluOp,
            memDataWrite => memDataWrite,
            memAdd => memAdd,
            memWrite => memWrite,
            loadQ => loadQ,
            readQ => readQ,
            regWD => regWD,
            regWrite => regWrite,
            operand2 => operand2,
            jmp => jmp,
            je => je,
            jg => jg,
            jne => jne,
            reg1 => reg1,
            reg2 => reg2,
            seg => seg,
            imm_offset => imm_offset,
            d => d,
            w => w,
            enableRead => enableRead,
            next_state_debug => next_state_debug,
            rd1 => rd1,
            rd2 => rd2,
            resetqueue => resetqueue,
            result => result,
            flags => flags
        );

    clk_process : process
    begin
        while true loop
            clk <= '0';
            wait for 10 ns;
            clk <= '1';
            wait for 10 ns;
        end loop;
    end process;

    stimulus: process
    begin
        enable <= '1';
        wait;
    end process;

end Behavioral;
