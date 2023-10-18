library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.ALL;
use ieee.std_logic_misc.all;
use IEEE.STD_LOGIC_arith.all;
library UNISIM;
use UNISIM.VComponents.all;


entity adder_8_bits is

    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           --C_IN : in STD_LOGIC;
           --C_out : out STD_LOGIC;
           Sum : out STD_LOGIC_VECTOR (7 downto 0));
end adder_8_bits;

architecture Behavioral of adder_8_bits is
signal result : std_logic_vector(8 downto 0);

COMPONENT adder_four_bits IS  
PORT(--Cin: IN STD_LOGIC;
     A:IN STD_LOGIC_VECTOR(3 DOWNTO 0);
     B:IN STD_LOGIC_VECTOR(3 DOWNTO 0);
     S:OUT STD_LOGIC_VECTOR(3 DOWNTO 0);
     Cout:OUT STD_LOGIC);
END COMPONENT adder_four_bits;

COMPONENT adder_four_bits2 IS  
PORT(
     A:IN STD_LOGIC_VECTOR(3 DOWNTO 0);
     B:IN STD_LOGIC_VECTOR(3 DOWNTO 0);
     Cin: IN STD_LOGIC;
     S:OUT STD_LOGIC_VECTOR(3 DOWNTO 0));
     --Cout:OUT STD_LOGIC);
END COMPONENT adder_four_bits2;

SIGNAL SC:STD_LOGIC; 

begin

U1:adder_four_bits    
        PORT MAP(--Cin=>C_in,
                    A=>A(3 DOWNTO 0),
                    B=>B(3 DOWNTO 0),
                    S=>Sum(3 DOWNTO 0),Cout=>SC);

U2:adder_four_bits2   
        PORT MAP(       A=>A(7 DOWNTO 4),           
                            B=>B(7 DOWNTO 4),
                            Cin=>SC,
                            S=>Sum (7 DOWNTO 4));--,Cout=>C_out);
                            
-- C_out <= '0';

end Behavioral;
