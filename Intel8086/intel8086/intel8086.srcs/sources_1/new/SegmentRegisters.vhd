----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 13.11.2024 10:53:30
-- Design Name: 
-- Module Name: SegmentRegisters - Behavioral
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

entity SegmentRegisters is
Port (segAddress: in std_logic_vector(1 downto 0);
    offset: in std_logic_vector(15 downto 0);
    memAddress: out std_logic_vector(15 downto 0);
    segmentValue: out std_logic_vector(15 downto 0) );
end SegmentRegisters;

architecture Behavioral of SegmentRegisters is
type rom_type is array (0 to 3) of std_logic_vector(15 downto 0);
signal rom : rom_type := ( 0 => x"0000", -- CS
                           1 => x"0040", -- DS
                           2 => x"0100", -- SS
                           3 => x"1000"); -- ES
signal segOffset: std_logic_vector(15 downto 0);
begin

    segOffset <= rom(conv_integer(segAddress));
    memAddress <= segOffset + offset;
    segmentValue <= segOffset;

end Behavioral;
