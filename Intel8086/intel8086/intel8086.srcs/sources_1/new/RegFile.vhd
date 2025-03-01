library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity RegFile is
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
end RegFile;

architecture Behavioral of RegFile is
type reg_array is array(0 to 7) of std_logic_vector(15 downto 0);
signal reg_file : reg_array := (others => X"0000");
-- 0 - AX, 1 - CX, 2 - DX, 3 - BX, 4 - SP, 5 - BP, 6 - SI, 7 - DI
signal regdata1, regdata2: std_logic_vector(15 downto 0) := x"0000";
signal wa: std_logic_vector(2 downto 0) := "000";  
signal low_high: std_logic := '0';  
signal wd: std_logic_vector(15 downto 0) := x"0000";  
begin

    wd <= memData when regWD = "00" else
    imm when regWD = "01" else
    aluRes when regWD = "10" else
    readreg2;

    process(clk, en, regWrite, w, low_high, wd)
    begin
        if rising_edge(clk) then
            if en = '1' and regWrite = '1' then
                if w = '1' then 
                    reg_file(conv_integer(wa)) <= wd;  
                elsif w = '0' then
                    if low_high = '0' then 
                        reg_file(conv_integer(wa))(7 downto 0) <= wd(7 downto 0);  
                    elsif low_high = '1' then 
                        reg_file(conv_integer(wa))(15 downto 8) <= wd(15 downto 8);  
                    end if;
                end if;
            end if;
        end if;
    end process;

    process(w, reg1, reg2, reg_file)
    begin
        if w = '1' then
            rd1 <= reg_file(conv_integer(reg1));
            rd2 <= reg_file(conv_integer(reg2));
            wa <= reg1;  
        elsif w = '0' then  
            case reg1 is
                when "000" => regdata1 <= reg_file(0); -- AL
                              rd1 <= "00000000" & regdata1(7 downto 0);  -- AL (byte inferior)
                              low_high <= '0';
                              wa <= "000";
                when "001" => regdata1 <= reg_file(1); -- CL
                              rd1 <= "00000000" & regdata1(7 downto 0);  -- CL (byte inferior)
                              low_high <= '0';
                              wa <= "001";
                when "010" => regdata1 <= reg_file(2); -- DL
                              rd1 <= "00000000" & regdata1(7 downto 0);  -- DL (byte inferior)
                              low_high <= '0';
                              wa <= "010";
                when "011" => regdata1 <= reg_file(3); -- BL
                              rd1 <= "00000000" & regdata1(7 downto 0);  -- BL (byte inferior)
                              low_high <= '0';
                              wa <= "011";
                when "100" => regdata1 <= reg_file(0); -- AH
                              rd1 <= regdata1(15 downto 8) & "00000000"; -- AH (byte superior)
                              low_high <= '1';
                              wa <= "000";
                when "101" => regdata1 <= reg_file(1); -- CH
                              rd1 <= regdata1(15 downto 8) & "00000000"; -- CH (byte superior)
                              low_high <= '1';
                              wa <= "001";
                when "110" => regdata1 <= reg_file(2); -- DH
                              rd1 <= regdata1(15 downto 8) & "00000000"; -- DH (byte superior)
                              low_high <= '1';
                              wa <= "010";
                when "111" => regdata1 <= reg_file(3); -- BH
                              rd1 <= regdata1(15 downto 8) & "00000000"; -- BH (byte superior)
                              low_high <= '1';
                              wa <= "011";
                when others => rd1 <= x"0000"; low_high <= '0';    
            end case;
            -- Citirea din reg2
            case reg2 is
                when "000" => regdata2 <= reg_file(0); -- AL
                              rd2 <= "00000000" & regdata2(7 downto 0); -- AL (byte inferior)
                              low_high <= '0';
                when "001" => regdata2 <= reg_file(1); -- CL
                              rd2 <= "00000000" & regdata2(7 downto 0); -- CL (byte inferior)
                              low_high <= '0';
                when "010" => regdata2 <= reg_file(2); -- DL
                              rd2 <= "00000000" & regdata2(7 downto 0); -- DL (byte inferior)
                              low_high <= '0';
                when "011" => regdata2 <= reg_file(3); -- BL
                              rd2 <= "00000000" & regdata2(7 downto 0); -- BL (byte inferior)
                              low_high <= '0';
                when "100" => regdata2 <= reg_file(0); -- AH
                              rd2 <= regdata2(15 downto 8) & "00000000"; -- AH (byte superior)
                              low_high <= '1';
                when "101" => regdata2 <= reg_file(1); -- CH
                              rd2 <= regdata2(15 downto 8) & "00000000"; -- CH (byte superior)
                              low_high <= '1';
                when "110" => regdata2 <= reg_file(2); -- DH
                              rd2 <= regdata2(15 downto 8) & "00000000"; -- DH (byte superior)
                              low_high <= '1';
                when "111" => regdata2 <= reg_file(3); -- BH
                              rd2 <= regdata2(15 downto 8) & "00000000"; -- BH (byte superior)
                              low_high <= '1';
                when others => rd2 <= x"0000"; low_high <= '0';    
            end case;
        end if;
    end process;

--    process(regWD, imm, memData, readreg2, aluRes)
--    begin
--        case regWD is
--            when "00" => wd <= memData;  -- Date din memorie
--            when "01" => wd <= imm;     -- Date din imediat
--            when "10" => wd <= aluRes;  -- Rezultatul ALU
--            when "11" => wd <= readreg2; -- Date din reg2
--            when others => wd <= x"0000";
--        end case;
--    end process;
   

end Behavioral;
