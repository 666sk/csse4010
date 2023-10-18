% Project: Design of low complexity and high-throughput digital hardware
% architecture for a multi-beam array receiver using approximate computing
% (i.e., approximate algorithm in conjution with approximate hardware)

% MATLAB code for hardware ADFT (TASK 2&3)
% Change the simulation file name to switch between task 2&3 (See comments below)
clear; close all; clc;

N_antenna = 8;
ang = (0:0.5:179);

Beams_adft = zeros(N_antenna, length(ang));
Beams_hrd = zeros(N_antenna, length(ang));

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
 
 %word length
 W = 8; %Word length for both accurate adders and approximate adders
 D = 4;
 WIN = 8; %ADC input
 DIN = 4;
  
  
for deg = 0:0.5:179
    
    deg1 = deg - 90;
    si = pi*(deg1)/180;
    
    w = zeros(N_antenna, NN);
    y_adft = zeros(N_antenna, NN);
       
    
    for n1 = 1:N_antenna
        for n2 = 1:NN
            ll=(-1)*n1*sin(si)+n2-80;
            w(n1,n2)=exp(-1i*0.99*pi*ll);
        end
    end
    
    %%Real input
    v0r = zeros(NN,2);
    v0r(:,1) = 1:NN;
    v0r(:,2) = real(w(1,:)).';
    v1r = zeros(NN,2);
    v1r(:,1) = 1:NN;
    v1r(:,2) = real(w(2,:)).';
    v2r = zeros(NN,2);
    v2r(:,1) = 1:NN;
    v2r(:,2) = real(w(3,:)).';
    v3r = zeros(NN,2);
    v3r(:,1) = 1:NN;
    v3r(:,2) = real(w(4,:)).';
    v4r = zeros(NN,2);
    v4r(:,1) = 1:NN;
    v4r(:,2) = real(w(5,:)).';
    v5r = zeros(NN,2);
    v5r(:,1) = 1:NN;
    v5r(:,2) = real(w(6,:)).';
    v6r = zeros(NN,2);
    v6r(:,1) = 1:NN;
    v6r(:,2) = real(w(7,:)).';
    v7r = zeros(NN,2);
    v7r(:,1) = 1:NN;
    v7r(:,2) = real(w(8,:)).';

    %%Imaginary input
    v0i = zeros(NN,2);
    v0i(:,1) = 1:NN;
    v0i(:,2) = imag(w(1,:)).';
    v1i = zeros(NN,2);
    v1i(:,1) = 1:NN;
    v1i(:,2) = imag(w(2,:)).';
    v2i = zeros(NN,2);
    v2i(:,1) = 1:NN;
    v2i(:,2) = imag(w(3,:)).';
    v3i = zeros(NN,2);
    v3i(:,1) = 1:NN;
    v3i(:,2) = imag(w(4,:)).';
    v4i = zeros(NN,2);
    v4i(:,1) = 1:NN;
    v4i(:,2) = imag(w(5,:)).';
    v5i = zeros(NN,2);
    v5i(:,1) = 1:NN;
    v5i(:,2) = imag(w(6,:)).';
    v6i = zeros(NN,2);
    v6i(:,1) = 1:NN;
    v6i(:,2) = imag(w(7,:)).';
    v7i = zeros(NN,2);
    v7i(:,1) = 1:NN;
    v7i(:,2) = imag(w(8,:)).';
    
    %ADFT
    for n2=1:NN
        y_adft(:,n2) = (F8*w(:,n2));
    end
    
    %Run simulation
    %Change name to different file name to run different simulations
    %For example,
    %File name: 'ADFT' -- The initial un-optimized ADFT design
    %File name: 'Optimized_ADFT' -- The optimized ADFT design after
    %pipelining
    %File name: 'ApproximateAdder' -- The final design with approximate
    %adders
    out=sim('ApproximateAdder',1024);
    
    
    %Output from hardware -- time series
    y0r = out.y0r.Data(2:end)';
    y1r = out.y1r.Data(2:end)';
    y2r = out.y2r.Data(2:end)';
    y3r = out.y3r.Data(2:end)';
    y4r = out.y4r.Data(2:end)';
    y5r = out.y5r.Data(2:end)';
    y6r = out.y6r.Data(2:end)';
    y7r = out.y7r.Data(2:end)';


    y0i = out.y0i.Data(2:end)';
    y1i = out.y1i.Data(2:end)';
    y2i = out.y2i.Data(2:end)';
    y3i = out.y3i.Data(2:end)';
    y4i = out.y4i.Data(2:end)';
    y5i = out.y5i.Data(2:end)';
    y6i = out.y6i.Data(2:end)';
    y7i = out.y7i.Data(2:end)';
    
    y0 = complex(y0r,y0i);
    y1 = complex(y1r,y1i);
    y2 = complex(y2r,y2i);
    y3 = complex(y3r,y3i);
    y4 = complex(y4r,y4i);
    y5 = complex(y5r,y5i);
    y6 = complex(y6r,y6i);
    y7 = complex(y7r,y7i);
    
    yhrd=zeros(8,NN);
    yhrd(1,:)=y0(1:1024);
    yhrd(2,:)=y1(1:1024);
    yhrd(3,:)=y2(1:1024);
    yhrd(4,:)=y3(1:1024);
    yhrd(5,:)=y4(1:1024);
    yhrd(6,:)=y5(1:1024);
    yhrd(7,:)=y6(1:1024);
    yhrd(8,:)=y7(1:1024);
      
    Beam_energy_adft = sum((abs(y_adft).^2),2);
    Beam_energy_adft = Beam_energy_adft/NN;
    
    Beam_energy_hrd = sum((abs(yhrd).^2),2);
    Beam_energy_hrd = Beam_energy_hrd/NN;
    
    Beams_adft(:,2*deg+1) = Beam_energy_adft;
    Beams_hrd(:,2*deg+1) = Beam_energy_hrd;

end

Beams_adft = Beams_adft/max(max(abs(Beams_adft)));
for pp=1:8
    Beams_hrd(pp,:) = Beams_hrd(pp,:)/max(max(abs(Beams_hrd(pp,:))));
end
    
%Plot in polar domain      
figure(1)
for pp=1:8
    polarplot(ang*pi/180,Beams_hrd(pp,:))
    hold on;
end
title('Beam profile - Polar domain - Hardware output (Optimized with approximate adders)');

%Plot in Cartesian domain
figure(2)
for pp=1:8
    plot(ang,Beams_hrd(pp,:))
    hold on;
end
title('Beam profile - Cartesian domain - Hardware output (Optimized with approximate adders)');
xlabel('angle (deg)');
ylabel('Normalised Gain');

%Plot in Cartesian domain in dB
figure(3)
for pp=1:8
    plot(ang,20*log10(Beams_hrd(pp,:)))
    hold on;
end
title('Beam profile - Cartesian domain - Hardware output (Optimized with approximate adders)');
xlabel('angle (deg)');
ylabel('Normalised Gain in dB');

%Change variable kk to see different beams -- kk-> 1~8
kk = 1;
figure(4)
plot(ang, Beams_adft(kk,:)/max(abs(Beams_adft(kk,:))));
hold on
plot(ang, Beams_hrd(kk,:)/max(abs(Beams_hrd(kk,:))));
title('Comparsion between Hardware ADFT and Software FFT (Beam 1)');
legend('Software FFT','Hardware ADFT using approximate adders');
