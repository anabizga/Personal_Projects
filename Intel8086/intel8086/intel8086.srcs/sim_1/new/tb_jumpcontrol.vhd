----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 08.11.2024 18:12:07
-- Design Name: 
-- Module Name: tb_jumpcontrol - Behavioral
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

entity tb_jumpcontrol is
--  Port ( );
end tb_jumpcontrol;

architecture Behavioral of tb_jumpcontrol is

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

signal ip: std_logic_vector(15 downto 0) := (others => '0');
signal jump: std_logic := '0';
signal je: std_logic := '0';
signal jne: std_logic := '0';
signal jg: std_logic := '0';
signal flags: std_logic_vector(8 downto 0) := (others => '0');
signal jumpAddress: std_logic_vector(15 downto 0) := (others => '0');
signal nextIp: std_logic_vector(15 downto 0);
signal resetqueue: std_logic;
begin

    uut: JumpControl Port map (ip => ip,
              jump => jump,
              je => je,
              jne => jne,
              jg => jg,
              flags => flags,
              jumpAddress => jumpAddress,
              nextIp => nextIp,
              resetqueue => resetqueue );

    -- Test Process
    stim_proc: process
    begin
        -- Test case 1: No jump
        ip <= "0000000000000001"; 
        jump <= '0';
        je <= '0';
        jne <= '0';
        jg <= '0';
        flags <= "000000000"; 
        jumpAddress <= "0000000000000010"; 
        wait for 10 ns; 
       
        -- Test case 2: Jump
        jump <= '1'; 
        wait for 10 ns; 
        
        -- Test case 3: Jump if equal (JE)
        jump <= '0'; 
        je <= '1'; 
        flags <= "000000001"; 
        wait for 10 ns; 
        jump <= '0'; 
        je <= '1'; 
        flags <= "000000000"; 
        wait for 10 ns; 
        
        -- Test case 4: Jump if not equal (JNE)
        jump <= '0'; 
        je <= '0'; 
        jne <= '1'; 
        flags <= "000000000"; 
        wait for 10 ns; 
        jump <= '0'; 
        je <= '0'; 
        jne <= '1'; 
        flags <= "000000001"; 
        wait for 10 ns; 
        
        -- Test case 5: Jump if greater (JG)
        jump <= '0'; 
        je <= '0'; 
        jne <= '0'; 
        jg <= '1'; 
        flags <= "000000000"; 
        wait for 10 ns; 
        jump <= '0'; 
        je <= '0'; 
        jne <= '0'; 
        jg <= '1'; 
        flags <= "000000001"; 
        wait for 10 ns; 
        
        -- End simulation
        wait;
    end process;

end Behavioral;
