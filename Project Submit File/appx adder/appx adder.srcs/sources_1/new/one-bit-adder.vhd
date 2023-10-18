

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library UNISIM;
use UNISIM.VComponents.all;


entity onebitadder is
Port (    A: in std_logic;
			B: in std_logic;
			C_in: in std_logic;
			Sum: out std_logic;
			C_out: out std_logic);
end onebitadder;

architecture Behavioral of onebitadder is

begin

LUT_U0: LUT3 
generic map (INIT => X"8E")--truth table for adding
    port map(
        I0 => C_in, 
        I1 => B, 
        I2 => A, 
        O => Sum  
);

C_out <= A;

end Behavioral;
