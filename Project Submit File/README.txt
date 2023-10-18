The VHDL source file for the 8-bit approximate adder is located at:
'appx adder' -> 'appx adders.srcs' -> 'sources_1' -> 'new' -> 'adder_8_bits.vhd'

However, to config the MATLAB code for the path, have to include all the child components, 
for example, 'adder_four_bits.vhd'.



For Model Composer Simulink .slx files:

'ADFT.slx' -> The initial hardware design before pipelining for Task2(a)

'Optimized_ADFT.slx' -> The optimized hardware design after pipelining for Task2(b)

'ApproximateAdder.slx' -> The final design after replacing the accurate adders with approximate adders for Task3



For MATLAB script files:

'Multi_Beamformer_1.m' -> The provided MATLAB script using MATLAB FFT function

'Multi_Beamformer_2.m' -> The MATLAB script for software ADFT Algorithms for Task1

'Test2.m' -> The MATLAB script for hardware ADFT Algorithms for Task2&3, change parameters to switch between tasks(See comments)

'adder_8_bits_config.m' -> The MATLAB config file for implementing the blackbox in Model Composer

