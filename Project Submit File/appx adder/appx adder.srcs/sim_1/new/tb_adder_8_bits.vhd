library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;


entity tb_adder_8_bits is
--  Port ( );
end tb_adder_8_bits;

architecture Behavioral of tb_adder_8_bits is

component adder_8_bits 
    PORT (
    A,B : in STD_LOGIC_VECTOR (7 downto 0);
    --C_IN: in STD_LOGIC;
    --C_out : out STD_LOGIC;
    Sum : out STD_LOGIC_VECTOR (7 downto 0)
    );
end component;

signal A,B : std_logic_vector (7 downto 0);
--signal C_in : std_logic;
signal result : std_logic_vector(7 downto 0);


begin

UUT: adder_8_bits port map (
    A => A,
    B => B,
    --C_IN => C_in,
    --C_out => C_out,
    Sum => result
    );

start : process
    begin
    A <= "00000001";
    B <= "00000000";
    
    wait for 5ps;
    
    A <= "00000111";
    B <= "00000010";
    wait for 5ps;
    
    A <= "00000010";
    B <= "00000011";
    wait for 5ps;
    
    A <= "00000011";
    B <= "00000010";
    wait for 5ps;
    
    A <= "01000000";
    B <= "00100000";
    wait for 5ps;
    
    A <= "01000010";
    B <= "00001000";
    wait for 5ps;
    
    wait;
    
end process start;

end Behavioral;