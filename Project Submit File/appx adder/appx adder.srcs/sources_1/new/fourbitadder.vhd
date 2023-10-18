library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
--This is a 4-bits adder

entity fourbitadder is
    Port ( A : in STD_LOGIC_VECTOR (3 downto 0);
           B : in STD_LOGIC_VECTOR (3 downto 0);
           Cin : in STD_LOGIC;
           S : out STD_LOGIC_VECTOR (3 downto 0);
           Cout : out STD_LOGIC);
end adder_four_bits;

architecture Behavioral of adder_four_bits is

component adder_one_bit
    port (
        A, B, Cin : in std_logic;
        S, Cout : out std_logic);
end component;

signal temp: std_logic_vector(4 downto 0);

begin
    temp(0) <= Cin;
    Cout <= temp(4);
    gen: for i in 0 to 3 generate
        U1: adder_one_bit port map (
            A(I),B(I),TEMP(I),S(I),temp(I+1));
    end generate gen;

end Behavioral;