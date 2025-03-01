----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 08.11.2024 17:47:59
-- Design Name: 
-- Module Name: JumpControl - Behavioral
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

entity JumpControl is
Port (ip: in std_logic_vector(15 downto 0);
    jump: in std_logic;
    je: in std_logic;
    jne: in std_logic;
    jg: in std_logic;
    flags: in std_logic_vector(8 downto 0);
    jumpAddress: in std_logic_vector(15 downto 0);
    nextIp: out std_logic_vector(15 downto 0);
    resetqueue: out std_logic );
end JumpControl;

architecture Behavioral of JumpControl is
signal zero, sign, resultJE, resultJNE, resultJG, resultOR: std_logic;
begin

    zero <= flags(0);
    sign <= flags(1);

    resultJE <= je and zero;
    resultJNE <= jne and (not zero);
    resultJG <= jg and ( (not zero) and (not sign) );
    
    resultOR <= jump or resultJE or resultJNE or resultJG;
    
    nextIp <= ip when resultOR = '0' else jumpAddress;
    resetqueue <= resultOR;

end Behavioral;
