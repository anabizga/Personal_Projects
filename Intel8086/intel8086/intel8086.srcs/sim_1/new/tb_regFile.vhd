----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 13.11.2024 12:35:17
-- Design Name: 
-- Module Name: tb_regFile - Behavioral
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

entity tb_regFile is
--  Port ( );
end tb_regFile;

architecture behavior of tb_regFile is
    component RegFile
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
          rd2: out std_logic_vector(15 downto 0));
    end component;

    -- Signals
    signal clk, en: std_logic := '0';
    signal reg1: std_logic_vector(2 downto 0) := "000";
    signal reg2: std_logic_vector(2 downto 0) := "001";
    signal w: std_logic := '1';
    signal regWrite: std_logic := '1';
    signal regWD: std_logic_vector(1 downto 0) := "00";  -- 00 for memData
    signal imm: std_logic_vector(15 downto 0) := "0000000000000001";
    signal memData: std_logic_vector(15 downto 0) := "0000000000000101";
    signal aluRes: std_logic_vector(15 downto 0) := "0000000000000110";
    signal readreg2: std_logic_vector(15 downto 0) := "0000000000001000";
    signal rd1: std_logic_vector(15 downto 0);
    signal rd2: std_logic_vector(15 downto 0);

begin

    uut: RegFile
    Port map (clk => clk,
              en => en,
              reg1 => reg1,
              reg2 => reg2,
              w => w,
              regWrite => regWrite,
              regWD => regWD,
              imm => imm,
              memData => memData,
              aluRes => aluRes,
              readreg2 => readreg2,
              rd1 => rd1,
              rd2 => rd2);

    clk_process: process
    begin
        clk <= '0';
        wait for 5 ns;
        clk <= '1';
        wait for 5 ns;
    end process;

    stim_proc: process
    begin
        en <= '1';
        -- Test case 1: Writing to register 0 (AX) with memData
        reg1 <= "000";  -- AX
        reg2 <= "001";  -- CX
        regWD <= "00";  -- Use memData for write data
        memData <= "0000000000000111";  
        w <= '1';
        regWrite <= '1';       
        wait for 10 ns;
        
        -- Test case 2: Writing to register 1 (BX) with ALU result
        reg1 <= "011";  -- BX
        regWD <= "10";  -- Use ALU result for write data
        aluRes <= "0000000000001000"; 
        w <= '1';       
        regWrite <= '1'; 
        wait for 10 ns;
        
        -- Test case 3: Reading Ax and BX
        reg1 <= "000";  -- AX
        reg2 <= "011";  -- BX
        w <= '1';       
        regWrite <= '0';
        wait for 10 ns;

        -- Test case 4: Writing to register AH with imm value
        reg1 <= "100";  -- AX (for AH)
        reg2 <= "111";  -- BX (for BH)
        regWD <= "01";  -- Use imm for write data
        imm <= "1111000000000000";  
        w <= '0';
        regWrite <= '1';
        wait for 10 ns;

        -- Test case 5: Writing to register BL with imm value
        reg1 <= "011";  -- BX (for BL)
        reg2 <= "000";  -- AX (for AL)
        regWD <= "01";  -- Use imm for write data
        imm <= "0000000000001110";  -- 0x000E
        w <= '0';
        regWrite <= '1';
        wait for 10 ns;
        
        -- Test case 6: Reading AH and BH
        reg1 <= "100";  -- AH
        reg2 <= "111";  -- BH
        w <= '0';       
        regWrite <= '0';
        wait for 10 ns;
        
        -- Test case 6: Reading AL and BL
        reg1 <= "000";  -- AL
        reg2 <= "011";  -- BL
        w <= '0';       
        regWrite <= '0';
        wait for 10 ns;
        
        -- Test case 7: Reading AX and BX
        reg1 <= "000";  -- AX
        reg2 <= "011";  -- BX
        w <= '1';       
        regWrite <= '0';
        wait for 10 ns;

        -- End simulation
        wait;
    end process;

end behavior;
