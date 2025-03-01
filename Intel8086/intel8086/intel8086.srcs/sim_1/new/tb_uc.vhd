----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 27.11.2024 14:18:51
-- Design Name: 
-- Module Name: tb_uc - Behavioral
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

entity tb_uc is
--  Port ( );
end tb_uc;

architecture Behavioral of tb_uc is
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

   signal clk : std_logic := '0';
    signal reset, enable : std_logic := '0';
    signal instruction : std_logic_vector(31 downto 0) := (others => '0');
    signal aluOp : std_logic_vector(2 downto 0);
    signal memDataWrite : std_logic;
    signal memAdd : std_logic;
    signal enableNextIp, enableRead, enableFlags: std_logic;
    signal memWrite : std_logic;
    signal loadQ : std_logic;
    signal readQ : std_logic;
    signal regWD : std_logic_vector(1 downto 0);
    signal regWrite : std_logic;
    signal operand2 : std_logic_vector(1 downto 0);
    signal jmp : std_logic;
    signal je : std_logic;
    signal jg : std_logic;
    signal jne : std_logic;
    signal reg1 : std_logic_vector(2 downto 0);
    signal reg2 : std_logic_vector(2 downto 0);
    signal seg : std_logic_vector(1 downto 0);
    signal imm_offset : std_logic_vector(15 downto 0);
    signal d : std_logic;
    signal w : std_logic;
    signal next_state_debug : std_logic_vector(2 downto 0);
    constant clk_period : time := 10 ns;
begin
    
    -- Instantiate the Unit Under Test (UUT)
    uut: UC
        Port map (
            clk => clk,
            reset => reset,
            enable => enable,
            instruction => instruction,
            aluOp => aluOp,
            memDataWrite => memDataWrite,
            memAdd => memAdd,
            enableNextIp => enableNextIp,
            enableRead => enableRead,
            enableFlags => enableFlags,
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
            next_state_debug => next_state_debug
        );

      clk_process : process
        begin
        while true loop
            clk <= '0';
            wait for clk_period / 2;
            clk <= '1';
            wait for clk_period / 2;
        end loop;
        wait;
        end process;

    -- Stimulus process
    stim_proc: process
    begin
        instruction <= "10001001110000110000000000000000"; -- mov ax, bx
        enable <= '1';
        wait for 5 * clk_period;
        
        instruction <= "10001001000001100000000100000000"; -- mov [100H], ax
        wait for 5 * clk_period;
        
        instruction <= "10001011000001100000000100000000"; -- mov ax, [100H]
        wait for 5 * clk_period;
        
        instruction <= "10111000000000000000010100000000"; -- mov ax, 5
        wait for 5 * clk_period;
        
        instruction <= "00000001110000110000000000000000"; -- add ax, bx
        wait for 5 * clk_period;
        
        instruction <= "00000001000001100000000100000000"; -- add ax, [100H]
        wait for 5 * clk_period;
        
        instruction <= "00000011000001100000000100000000"; -- add [100H], ax
        wait for 5 * clk_period;
        
        instruction <= "00101001110000110000000000000000"; -- sub ax, bx
        wait for 5 * clk_period;
        
        instruction <= "00111001110000110000000000000000"; -- cmp ax, bx
        wait for 5 * clk_period;
        
        instruction <= "10000001110110000000000000000101"; -- add bx, 5
        wait for 5 * clk_period;
        
        instruction <= "10000001110111010000000000000101"; -- sub bx, 5
         wait for 5 * clk_period;
        
        instruction <= "10000001110111110000000000000101"; -- cmp bx, 5
         wait for 5 * clk_period;
        
        instruction <= "00100001110000110000000000000000"; -- and ax,bx
        wait for 5 * clk_period;
        
        instruction <= "00001001110000110000000000000000"; -- or axx, bx
        wait for 5 * clk_period;
        
        instruction <= "00110001110000110000000000000000"; -- xor ax, bx
        wait for 5 * clk_period;
        
        instruction <= "10000001110111000000000000000101"; -- and bx, 5
        wait for 5 * clk_period;
        
        instruction <= "10000001110110010000000000000101"; -- or bx, 5
        wait for 5 * clk_period;
        
        instruction <= "10000001110111100000000000000101"; -- xor bx, 5
        wait for 5 * clk_period;
        
        instruction <= "11101001000000000000000100000000"; -- jmp add
        wait for 5 * clk_period;
        
        instruction <= "01110100000000000000000100000000"; -- je add
        wait for 5 * clk_period;
        
        instruction <= "01110101000000000000000100000000"; -- jne add
        wait for 5 * clk_period;
        
        instruction <= "01111111000000000000000100000000"; -- jg add
        wait for 5 * clk_period;
    end process;

end Behavioral;
