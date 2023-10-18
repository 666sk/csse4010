library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
library UNISIM;
use UNISIM.VComponents.all;
--This is a 4-bits approximate adder

entity adder_four_bits2 is
    Port ( A : in STD_LOGIC_VECTOR (3 downto 0);
           B : in STD_LOGIC_VECTOR (3 downto 0);
           Cin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (3 downto 0));
           --Cout : out STD_LOGIC);
end adder_four_bits2;

architecture Behavioral of adder_four_bits2 is

component onebitadder_accurate
    port (
        A, B, C_in : in std_logic;
        Sum, C_out : out std_logic);
end component;

signal temp: std_logic_vector(4 downto 0);

begin
    temp(0) <= Cin;
    --Cout <= temp(4);
    gen: for i in 0 to 3 generate
        U1: onebitadder_accurate port map (
            A(I),B(I),TEMP(I),S(I),temp(I+1));
    end generate gen;

end Behavioral;