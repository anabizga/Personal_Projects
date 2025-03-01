----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 17.12.2024 09:09:36
-- Design Name: 
-- Module Name: eu - Behavioral
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

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity eu is
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
end eu;

architecture Behavioral of eu is
component UC
        Port (
            clk : in std_logic;
            reset : in std_logic;
            enable : in std_logic;
            instruction : in std_logic_vector(31 downto 0);
            aluOp : out std_logic_vector(2 downto 0);
            memDataWrite : out std_logic;
            memAdd : out std_logic;
            enableNextIp : out std_logic;
            memWrite : out std_logic;
            loadQ : out std_logic;
            readQ : out std_logic;
            regWD : out std_logic_vector(1 downto 0);
            regWrite : out std_logic;
            operand2 : out std_logic_vector(1 downto 0);
            jmp : out std_logic;
            je : out std_logic;
            jg : out std_logic;
            jne : out std_logic;
            reg1 : out std_logic_vector(2 downto 0);
            reg2 : out std_logic_vector(2 downto 0);
            seg : out std_logic_vector(1 downto 0);
            imm_offset : out std_logic_vector(15 downto 0);
            d : out std_logic;
            w : out std_logic;
            next_state_debug : out std_logic_vector(2 downto 0)
        );
    end component;
component RegFile is
Port (clk: in std_logic;
    en: in std_logic;
    reg1: in std_logic_vector(2 downto 0);
    reg2: in std_logic_vector(2 downto 0);
    w: in std_logic; 
    regWrite: in std_logic;  
    regWD: in std_logic_vector(1 downto 0);
    imm: in std_logic_vector(15 downto 0);
    memData: in std_logic_vector(15 downto 0);
    aluRes: in std_logic_vector(15 downto 0);
    readreg2: in std_logic_vector(15 downto 0);
    rd1: out std_logic_vector(15 downto 0);
    rd2: out std_logic_vector(15 downto 0) );
end component;
component JumpControl is
Port (ip: in std_logic_vector(15 downto 0);
    jump: in std_logic;
    je: in std_logic;
    jne: in std_logic;
    jg: in std_logic;
    flags: in std_logic_vector(8 downto 0);
    jumpAddress: in std_logic_vector(15 downto 0);
    nextIp: out std_logic_vector(15 downto 0);
    resetqueue: out std_logic );
end component;
component ALU is
Port (rd1: in std_logic_vector(15 downto 0);
    rd2: in std_logic_vector(15 downto 0);
    imm: in std_logic_vector(15 downto 0);
    memData: in std_logic_vector(15 downto 0);
    operand2: in std_logic_vector(1 downto 0);
    aluOp: in std_logic_vector(2 downto 0);
    memDataWrite: in std_logic;
    dir: in std_logic;
    result: out std_logic_vector(15 downto 0);
    flags: out std_logic_vector(8 downto 0);
    wdMem: out std_logic_vector(15 downto 0));
end component;
signal aluOpsignal: std_logic_vector(2 downto 0) := "000";
signal memDataWritesignal: std_logic :='0';
signal memAddsignal:  std_logic:='0';
signal memWritesignal:  std_logic:='0';
signal loadQsignal:  std_logic:='0';
signal readQsignal:  std_logic:='0';
signal regWDsignal:  std_logic_vector(1 downto 0):="00";
signal regWritesignal:  std_logic:='0';
signal operand2signal:  std_logic_vector(1 downto 0):="00";
signal jmpsignal:  std_logic:='0';
signal jesignal:  std_logic:='0';
signal jgsignal:  std_logic:='0';
signal jnesignal:  std_logic:='0';
signal reg1signal:  std_logic_vector(2 downto 0):="000";
signal reg2signal:  std_logic_vector(2 downto 0):="000";
signal segsignal:  std_logic_vector(1 downto 0):="00";
signal imm_offsetsignal, wdmemsignal:  std_logic_vector(15 downto 0):=x"0000";
signal dsignal, enableNextIpSignal:  std_logic:='0';
signal wsignal:  std_logic:='0';
signal rd1signal:  std_logic_vector(15 downto 0):=x"0000";
signal rd2signal:  std_logic_vector(15 downto 0):=x"0000";
signal resetqueuesignal:  std_logic:='0';
signal resultsignal:  std_logic_vector(15 downto 0):=x"0000";
signal flagssignal:  std_logic_vector(8 downto 0):="000000000";
begin

    uut_uc: UC
        Port map (
            clk => clk,
            reset => reset,
            enable => enable,
            instruction => instruction,
            aluOp => aluOpsignal,
            memDataWrite => memDataWritesignal,
            memAdd => memAddsignal,
            enableNextIp => enableNextIpSignal,
            memWrite => memWritesignal,
            loadQ => loadQsignal,
            readQ => readQsignal,
            regWD => regWDsignal,
            regWrite => regWritesignal,
            operand2 => operand2signal,
            jmp => jmpsignal,
            je => jesignal,
            jg => jgsignal,
            jne => jnesignal,
            reg1 => reg1signal,
            reg2 => reg2signal,
            seg => segsignal,
            imm_offset => imm_offsetsignal,
            d => dsignal,
            w => wsignal,
            next_state_debug => next_state_debug
        );
        
     uut_rf: RegFile
     Port map (clk => clk,
              en => '1',
              reg1 => reg1signal,
              reg2 => reg2signal,
              w => wsignal,
              regWrite => regWritesignal,
              regWD => regWDsignal,
              imm => imm_offsetsignal,
              memData => x"0000",
              aluRes => resultsignal,
              readreg2 => rd2signal,
              rd1 => rd1signal,
              rd2 => rd2signal);        
              
      uut_alu: ALU
        port map (
            rd1 => rd1signal,
            rd2 => rd2signal,
            imm => imm_offsetsignal,
            memData => x"0000",
            operand2 => operand2signal,
            aluOp => aluOpsignal,
            memDataWrite => memDataWritesignal,
            dir => dsignal,
            result => resultsignal,
            flags => flagssignal,
            wdMem => wdmemsignal
        );
        
        uut_jc: JumpControl Port map (ip => x"0001",
              jump => jmpsignal,
              je => jesignal,
              jne => jnesignal,
              jg => jgsignal,
              flags => flagssignal,
              jumpAddress => x"0002",
              nextIp => nextIp,
              resetqueue => resetqueue );
        
            aluOp <= aluOpsignal;
            memDataWrite <= memDataWritesignal;
            memAdd <= memAddsignal;
            memWrite <= memWritesignal;
            loadQ <= loadQsignal;
            readQ <= readQsignal;
            regWD <= regWDsignal;
            regWrite <= regWritesignal;
            operand2 <= operand2signal;
            jmp <= jmpsignal;
            je <= jesignal;
            jg <= jgsignal;
            jne <= jnesignal;
            reg1 <= reg1signal;
            reg2 <= reg2signal;
            seg <= segsignal;
            imm_offset <= imm_offsetsignal;
            d <= dsignal;
            w <= wsignal;
            rd1 <= rd1signal;
            rd2 <= rd2signal;
            result <= resultsignal;
            flags <= flagssignal;

end Behavioral;
