----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 25.11.2024 17:49:03
-- Design Name: 
-- Module Name: InstructionQueue - Behavioral
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

entity InstructionQueue is
Port (clk: in std_logic;
    reset: in std_logic;
    loadQ: in std_logic;
    read_enable: in std_logic;
    instruction: in std_logic_vector(31 downto 0);
    queueFull: out std_logic;
    queueEmpty: out std_logic;
    instructionOut: out std_logic_vector(31 downto 0) );
end InstructionQueue;

architecture Behavioral of InstructionQueue is
constant QUEUE_SIZE : integer := 6;
type queue_array is array(0 to QUEUE_SIZE-1) of std_logic_vector(31 downto 0);
signal queue : queue_array := (others => (others => '0'));
signal head  : integer range 0 to QUEUE_SIZE-1 := 0;
signal tail  : integer range 0 to QUEUE_SIZE-1 := 0; 
signal count : integer range 0 to QUEUE_SIZE := 0;

begin

    process(clk, reset)
    begin
        if reset = '1' then
            head <= 0;
            tail <= 0;
            count <= 0;
            instructionOut <= (others => '0');
        elsif rising_edge(clk) then
            if loadQ = '1' then
                if count < QUEUE_SIZE then
                    queue(tail) <= instruction;
                    tail <= (tail + 1) mod QUEUE_SIZE;
                    count <= count + 1;
                end if; 
            elsif read_enable = '1' and count > 0 then
                instructionOut <= queue(head);
                head <= (head + 1) mod QUEUE_SIZE;
                count <= count - 1;
            end if;
        end if;
    end process;

    queueFull <= '1' when count = QUEUE_SIZE else '0';

    queueEmpty <= '1' when count = 0 else '0';

end Behavioral;
