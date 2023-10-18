% Date: 12 October, 2022
% Author: Chamith Wijenayake
% Base Matlab code for CSSE4010 Project - 2022 Semester 2

% Project: Design of low complexity and high-throughput digital hardware
% architecture for a multi-beam array receiver using approximate computing
% (i.e. approximate algorithms in conjuction with approaximate hardware)


% This M-code demonstrates a multi-beam antenna array receiver which
% employs 8 antenna elements and produces 8 (narrowband) beams

% The code produces the beam patterns both in cartesian and polar plots.


clear; close all; clc;

% NO. of antennas in the uniform linear array (ULA)
N_antenna=8;

% angle vector to plot the beam patterns against. 
ang=(0:0.5:179);

%Variable to store the beams as functions of angle. There are 8 beams.
% Row k of Beams corresponds to beam k=1,2,...,8
Beams=zeros(N_antenna,length(ang));

%NUmber of time samples considered. This can be changed based on the
%simulink end time for simulation.
NN=1024;

% Loop to sweep across the angles from 0 to 180 degrees with 0.5 degree
% increments. 
for deg=0:0.5:179
    
    deg1=deg-90; %Angle as needed to obtain the input signal.
    si=pi*(deg1)/180; % direction of the input signal (point source) with respect to array X axis
    
    % 2D input signal on the array. This 2D input w models a plane wave
    % signal received by the array at an angle si. 
    w=zeros(N_antenna,NN);
    
    % The multi-beamformer output.
    y=zeros(N_antenna,NN); 
    
    %Producing the 2D input plane wave signal based on a 1D intensity
    %function of the form e^{j*omega_t}.
    % 不用太在意这一段 - generate signal
    for n1=1:N_antenna % spatial index
        for n2=1:NN % time index
            ll=(-1)*n1*sin(si)+n2-80;
            w(n1,n2)=exp(-1i*0.99*pi*ll);
        end
    end
    
    %Computing the multi-beamformer output. Here, the output is just the
    %spatial DFT (discrete Fourier transform) of input frame for each time
    %stamp. 
    
    for n2=1:NN
        y(:,n2)=fft(w(:,n2)); 
        % For each time stamp n2, the output of the 8 elements of the array is the spatial DFT of the 8 inputs to the array.
        % This is the algorithm that needs to be designed in hardware. 
    end
    
    %Computing the enery in each beam. i.e. sum the amplitude square for
    %each beam output along the time dimension and divide by the number of
    %time samples summed across.
    Beam_energy=sum((abs(y).^2),2);
    Beam_energy=Beam_energy/NN;
    
    %Storing the beam energy for the particular angle.
    Beams(:,2*deg+1)=Beam_energy;
    
end

Beams=Beams/max(max(abs(Beams)));

% Plotting the beam profiles in cartesian plot.We plot all the 8 beams on
% the same plot to see the multi-beam array response pattern.
figure(1)
for pp=1:8
    plot(ang,Beams(pp,:))
    hold on;
end
title('Beam profile - Cartesian domain - Software FFT output');
xlabel('angle (deg)');
ylabel('Normalised Gain');

figure(2)
for pp=1:8
    plot(ang,20*log10(Beams(pp,:)))
    hold on;
end
title('Beam profile - Cartesian domain - Software FFT output');
xlabel('angle (deg)');
ylabel('Normalised Gain in dB');

% Plotting the beam profiles in polar plot.We plot all the 8 beams on
% the same plot to see the multi-beam array response pattern.
figure(3)
for pp=1:8
    polarplot(ang*pi/180,Beams(pp,:))
    hold on;
end
title('Beam profile - Polar domain - Software FFT output');


