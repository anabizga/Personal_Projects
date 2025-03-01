----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 08.11.2024 16:05:09
-- Design Name: 
-- Module Name: ALU - Behavioral
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

entity ALU is
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
end ALU;

architecture Behavioral of ALU is
signal A,B,C: std_logic_vector(15 downto 0) := x"0000";
signal  flagsignal: std_logic_vector(8 downto 0) := "000000000";
begin

    A <= rd1;
    MUX_B: process(operand2, imm, memData, rd2)
    begin
        case operand2 is
            when "00" => B <= rd2;
            when "01" => B <= imm;
            when "11" => B <= memData;
            when others => B <= x"0000";
        end case;
    end process;
  
    op: process(aluOp, dir, A, B)
    begin
        case aluOp is
        when "000" => C <= A + B;
        when "001" => 
            if dir = '0' then C <= A - B;
            else C <= B - A;
            end if;
        when "010" => C <= A and B;
        when "011" => C <= A or B;
        when "100" => C <= A xor B;
        when others => C <= "XXXXXXXXXXXXXXXX";
        end case;
    end process;

    ZeroFlag: flagsignal(0) <= '1' when C = x"0000" else '0';
    ParityFlag: flagsignal(1) <= C(0);
    SignFlag: flagsignal(2) <= C(15);
    flagsignal(8 downto 3) <= "000000";
    
    process(clk, reset)
    begin
        if reset = '1' then
            flags <= (others => '0');
        elsif rising_edge(clk)  and enable = '1' then
            flags <= flagsignal;
        end if;
    end process;
    
    result <= C;
    
    writeDataMem: wdMem <= C when memDataWrite = '0' else rd1;

end Behavioral;
