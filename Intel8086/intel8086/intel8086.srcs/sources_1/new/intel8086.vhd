----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 13.12.2024 17:41:24
-- Design Name: 
-- Module Name: intel8086 - Behavioral
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
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity intel8086 is
Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (7 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
end intel8086;

architecture Behavioral of intel8086 is
component mpg is
Port (enable: out std_logic;
      btn: in std_logic;
      clk: in std_logic );
end component;
component SSD is
    Port ( clk : in STD_LOGIC;
           digits : in STD_LOGIC_VECTOR(31 downto 0);
           an : out STD_LOGIC_VECTOR(7 downto 0);
           cat : out STD_LOGIC_VECTOR(6 downto 0));
end component;
component InstructionDataFetch is
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
end component;
component SegmentRegisters is
Port (
    segAddress: in std_logic_vector(1 downto 0);
    offset: in std_logic_vector(15 downto 0);
    memAddress: out std_logic_vector(15 downto 0);
    segmentValue: out std_logic_vector(15 downto 0)
);
end component;
component InstructionQueue is
Port (
    clk: in std_logic;
    reset: in std_logic;
    loadQ: in std_logic;
    read_enable: in std_logic;
    instruction: in std_logic_vector(31 downto 0);
    queueFull: out std_logic;
    queueEmpty: out std_logic;
    instructionOut: out std_logic_vector(31 downto 0)
);
end component;
component UC
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
Port (clk: in std_logic;
    enable: in std_logic;
    reset: in std_logic;
    rd1: in std_logic_vector(15 downto 0);
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

signal offset: std_logic_vector(15 downto 0);
signal ip : std_logic_vector(15 downto 0) := x"0000";
signal loadQsignal: std_logic := '1';
signal readQsignal: std_logic := '1';
signal memWritesignal : std_logic := '0';
signal memAddsignal : std_logic := '0';
signal instructionreadsignal : std_logic := '0';
signal nextIpSignal: std_logic_vector(15 downto 0) := x"0000";
signal instructionSignal, result, instructionOutsignal: std_logic_vector(31 downto 0) := x"00000000";
signal memAddressSignal: std_logic_vector(15 downto 0) := x"0000";
signal segValueSignal, wdmemsignal: std_logic_vector(15 downto 0) := x"0000";
signal aluOpsignal: STD_LOGIC_VECTOR(2 downto 0);
signal memDataWritesignal: STD_LOGIC;
signal regWDsignal: STD_LOGIC_VECTOR(1 downto 0);
signal regWritesignal: STD_LOGIC;
signal operand2signal: STD_LOGIC_VECTOR(1 downto 0);
signal jmpsignal: STD_LOGIC;
signal jesignal: STD_LOGIC;
signal jgsignal: STD_LOGIC;
signal jnesignal: STD_LOGIC;
signal reg1signal, next_state_debug: STD_LOGIC_VECTOR(2 downto 0);
signal reg2signal: STD_LOGIC_VECTOR(2 downto 0);
signal imm_offsetsignal, datasignal: STD_LOGIC_VECTOR(15 downto 0);
signal dsignal: STD_LOGIC;
signal wsignal: STD_LOGIC;
signal segSignal: std_logic_vector(1 downto 0);
signal enableNextIpsignal, enableReadsignal: STD_LOGIC;
signal rd1signal, nextIp:  std_logic_vector(15 downto 0):=x"0000";
signal rd2signal:  std_logic_vector(15 downto 0):=x"0000";
signal resetqueuesignal, enableflagssignal:  std_logic:='0';
signal resultsignal:  std_logic_vector(15 downto 0):=x"0000";
signal flagssignal:  std_logic_vector(8 downto 0):="000000000";
signal enable, reset, queueFull, queueEmpty, resetqueue: std_logic := '0';
begin

   etichetaMPG: mpg port map(enable, btn(0), clk);
   reset <= btn(1);
    
    IP_Process: process(clk, reset)
    begin
        if reset = '1' then
            nextIpSignal <= x"0000";
        elsif rising_edge(clk) then 
            if enableNextIpsignal = '1' then 
                nextIpSignal <= nextIpSignal + 1;
            end if;
        end if;
    end process;

    uut_segRegisters: SegmentRegisters
    Port map (
        segAddress => segsignal,
        offset => imm_offsetsignal,
        memAddress => memAddressSignal,
        segmentValue => segValueSignal
    );
              
    uut_if: InstructionDataFetch
    Port map (
        clk => clk,
        ip => nextIpSignal,
        en => enableReadsignal,
        codsegment => segValueSignal,
        memAddress => memAddressSignal,
        wd => wdmemsignal,
        memWrite => memWritesignal,
        memAdd => memAddsignal,
        instruction => instructionSignal,
        data => datasignal
    ); 
        
    uut_instructionQueue: InstructionQueue
    Port map (
        clk => clk,
        reset => reset,
        loadQ => loadQsignal,
        read_enable => readQsignal,
        instruction => instructionSignal,
        queueFull => queueFull,
        queueEmpty => queueEmpty,
        instructionOut => instructionOutsignal
    );
    
    uut_uc: UC
        Port map (
            clk => clk,
            reset => reset,
            enable => enable,
            instruction => instructionOutsignal,
            aluOp => aluOpsignal,
            memDataWrite => memDataWritesignal,
            memAdd => memAddsignal,
            enableNextIp => enableNextIpSignal,
            enableRead => enableReadsignal,
            enableflags => enableflagssignal,
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
              memData => datasignal,
              aluRes => resultsignal,
              readreg2 => rd2signal,
              rd1 => rd1signal,
              rd2 => rd2signal);        
              
      uut_alu: ALU
        port map (
            clk => clk,
            enable => enableflagssignal,
            reset => reset,
            rd1 => rd1signal,
            rd2 => rd2signal,
            imm => imm_offsetsignal,
            memData => datasignal,
            operand2 => operand2signal,
            aluOp => aluOpsignal,
            memDataWrite => memDataWritesignal,
            dir => dsignal,
            result => resultsignal,
            flags => flagssignal,
            wdMem => wdmemsignal
        );
        
        uut_jc: JumpControl Port map (ip => nextIpSignal,
              jump => jmpsignal,
              je => jesignal,
              jne => jnesignal,
              jg => jgsignal,
              flags => flagssignal,
              jumpAddress => imm_offsetsignal,
              nextIp => nextIp,
              resetqueue => resetqueue );
    
   MUX: process(sw(7 downto 5), instructionOutsignal, nextIp, datasignal, memAddressSignal, imm_offsetsignal, rd1signal, rd2signal, resultsignal)
   begin
        case sw(7 downto 5) is
        when "000" => result <= instructionOutsignal;
        when "001" => result <= x"0000" & nextIp;
        when "010" => result <= x"0000" & datasignal;
        when "011" => result <= x"0000" & memAddressSignal;
        when "100" => result <= x"0000" & imm_offsetsignal;
        when "101" => result <= x"0000" & rd1signal;
        when "110" => result <= x"0000" & rd2signal;
        when "111" => result <= x"0000" & resultsignal;
        end case;  
   end process;
    
    etichetaSSD: ssd port map(clk,result,an,cat);
    --led(15 downto 0) <= memWrite & memAdd & loadQ & readq & queueFull & queueEmpty & memDataWrite & regWD & regWrite & d & w & jmp & je &jg &jne;

end Behavioral;
