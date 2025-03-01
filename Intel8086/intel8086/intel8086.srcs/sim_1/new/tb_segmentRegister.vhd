----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 13.11.2024 11:23:16
-- Design Name: 
-- Module Name: tb_segmentRegister - Behavioral
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

entity tb_segmentRegister is
--  Port ( );
end tb_segmentRegister;

architecture behavior of tb_segmentRegister is
    component SegmentRegisters
    Port (segAddress: in std_logic_vector(1 downto 0);
          offset: in std_logic_vector(15 downto 0);
          memAddress: out std_logic_vector(15 downto 0);
          segmentValue: out std_logic_vector(15 downto 0));
    end component;
    signal segAddress: std_logic_vector(1 downto 0);
    signal offset: std_logic_vector(15 downto 0);
    signal memAddress: std_logic_vector(15 downto 0);
    signal segmentValue: std_logic_vector(15 downto 0);
begin

    uut: SegmentRegisters
    Port map (segAddress => segAddress,
              offset => offset,
              memAddress => memAddress,
              segmentValue => segmentValue);

    stim_proc: process
    begin
        segAddress <= "00"; 
        offset <= x"1234";  
        wait for 10 ns;

        segAddress <= "01"; 
        offset <= x"1234";
        wait for 10 ns;
        
        segAddress <= "10"; 
        offset <= x"1234";
        wait for 10 ns;

        segAddress <= "11"; 
        offset <= x"1234";
        wait for 10 ns;

        -- End simulation
        wait;
    end process;

end behavior;
