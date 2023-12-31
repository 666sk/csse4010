
function adder_8_bits_config(this_block)

  % Revision History:
  %
  %   05-Nov-2022  (12:52 hours):
  %     Original code was machine generated by Xilinx's System Generator after parsing
  %     C:\Users\666sk\appx adder\appx adder.srcs\sources_1\new\adder_8_bits.vhd
  %
  %

  this_block.setTopLevelLanguage('VHDL');

  this_block.setEntityName('adder_8_bits');

  % System Generator has to assume that your entity  has a combinational feed through; 
  %   if it  doesn't, then comment out the following line:
  this_block.tagAsCombinational;

  this_block.addSimulinkInport('A');
  this_block.addSimulinkInport('B');

  this_block.addSimulinkOutport('Sum');

  Sum_port = this_block.port('Sum');
  Sum_port.setType('Fix_8_4');

  % -----------------------------
  if (this_block.inputTypesKnown)
    % do input type checking, dynamic output type and generic setup in this code block.

    if (this_block.port('A').width ~= 8);
      this_block.setError('Input data type for port "A" must have width=8.');
    end

    if (this_block.port('B').width ~= 8);
      this_block.setError('Input data type for port "B" must have width=8.');
    end

  end  % if(inputTypesKnown)
  % -----------------------------

  % System Generator found no apparent clock signals in the HDL, assuming combinational logic.
  % -----------------------------
   if (this_block.inputRatesKnown)
     inputRates = this_block.inputRates; 
     uniqueInputRates = unique(inputRates); 
     outputRate = uniqueInputRates(1);
     for i = 2:length(uniqueInputRates)
       if (uniqueInputRates(i) ~= Inf)
         outputRate = gcd(outputRate,uniqueInputRates(i));
       end
     end  % for(i)
     for i = 1:this_block.numSimulinkOutports 
       this_block.outport(i).setRate(outputRate); 
     end  % for(i)
   end  % if(inputRatesKnown)
  % -----------------------------

    uniqueInputRates = unique(this_block.getInputRates);


  % Add addtional source files as needed.
  %  |-------------
  %  | Add files in the order in which they should be compiled.
  %  | If two files "a.vhd" and "b.vhd" contain the entities
  %  | entity_a and entity_b, and entity_a contains a
  %  | component of type entity_b, the correct sequence of
  %  | addFile() calls would be:
  %  |    this_block.addFile('b.vhd');
  %  |    this_block.addFile('a.vhd');
  %  |-------------

  %    this_block.addFile('');
  %    this_block.addFile('');
  
  %Change to the correct path if run under a new enviornment
  this_block.addFile('../appx adder/appx adder.srcs/sources_1/new/one-bit-adder.vhd');
  this_block.addFile('../appx adder/appx adder.srcs/sources_1/new/onebitadder_accurate.vhd');
  this_block.addFile('../appx adder/appx adder.srcs/sources_1/new/adder_four_bits.vhd');
  this_block.addFile('../appx adder/appx adder.srcs/sources_1/new/adder_four_bits2.vhd');
  this_block.addFile('../appx adder/appx adder.srcs/sources_1/new/adder_8_bits.vhd');

return;


