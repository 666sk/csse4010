% Project: Design of low complexity and high-throughput digital hardware
% architecture for a multi-beam array receiver using approximate computing
% (i.e., approximate algorithm in conjution with approximate hardware)


% MATLAB code for software ADFT (TASK 1)
clear; close all; clc;

N_antenna = 8;
ang = (0:0.5:179);

Beams = zeros(N_antenna, length(ang));
Beams_adft = zeros(N_antenna, length(ang));

NN = 1024;
j = sqrt(-1);

%ADFT Matrix
F8 = [1           1           1           1           1           1           1               1;
      1       3/4*(1-j)      -j       -3/4*(1+j)     -1       -3/4*(1-j)      j       3/4*(1+j);
      1          -j          -1           j           1          -j          -1               j; 
      1      -3/4*(1+j)       j        3/4*(1-j)     -1        3/4*(1+j)     -j      -3/4*(1-j);
      1          -1           1          -1           1          -1           1              -1;
      1      -3/4*(1-j)      -j        3/4*(1+j)     -1        3/4*(1-j)      j      -3/4*(1+j);
      1           j          -1          -j           1           j          -1              -j;
      1       3/4*(1+j)       j       -3/4*(1-j)     -1       -3/4*(1+j)     -j       3/4*(1-j)];
  
for deg = 0:0.5:179
    
    deg1 = deg - 90;
    si = pi*(deg1)/180;
    
    w = zeros(N_antenna, NN);
    y = zeros(N_antenna, NN);
    y_adft = zeros(N_antenna, NN);
    
    for n1 = 1:N_antenna
        for n2 = 1:NN
            ll=(-1)*n1*sin(si)+n2-80;
            w(n1,n2)=exp(-1i*0.99*pi*ll);
        end
    end
    
    %FFT and ADFT
    for n2=1:NN
        y(:,n2)=fft(w(:,n2)); 
        y_adft(:,n2) = (F8*w(:,n2));
    end
      
    Beam_energy = sum((abs(y).^2),2);
    Beam_energy = Beam_energy/NN;
    
    Beam_energy_adft = sum((abs(y_adft).^2),2);
    Beam_energy_adft = Beam_energy_adft/NN;
    
    Beams(:,2*deg+1) = Beam_energy;
    Beams_adft(:,2*deg+1) = Beam_energy_adft;

end

Beams = Beams/max(max(abs(Beams)));
Beams_adft = Beams_adft/max(max(abs(Beams_adft)));

figure(1)
for pp=1:8
    plot(ang,Beams_adft(pp,:))
    hold on;
end
title('Beam profile - Cartesian domain - Software ADFT output');
xlabel('angle (deg)');
ylabel('Normalised Gain');

figure(2)
for pp=1:8
    plot(ang,20*log10(Beams_adft(pp,:)))
    hold on;
end
title('Beam profile - Cartesian domain - Software ADFT output');
xlabel('angle (deg)');
ylabel('Normalised Gain in dB');
      
      
figure(3)
for pp=1:8
    polarplot(ang*pi/180,Beams_adft(pp,:))
    hold on;
end
title('Beam profile - Polar domain - Software ADFT output');


kk = 5;
figure
plot(ang, Beams(kk,:)/max(abs(Beams(kk,:))));
hold on
plot(ang, Beams_adft(kk,:)/max(abs(Beams_adft(kk,:))));
title('Comparsion between Software ADFT and Software FFT (Beam 5)');
legend('Software FFT','Software ADFT');