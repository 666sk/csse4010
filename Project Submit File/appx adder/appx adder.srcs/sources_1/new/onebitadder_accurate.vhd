library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;


entity onebitadder_accurate is
Port (    A: in std_logic;
			B: in std_logic;
			C_in: in std_logic;
			Sum: out std_logic;
			C_out: out std_logic);
end onebitadder_accurate;

architecture Behavioral of onebitadder_accurate is

begin
    Sum <= (a xor b) xor C_in;
    C_out <= ((a XOR b)AND c_in)OR(a AND b);
end Behavioral;