# snils
## Description
the insurance number of an individual personal account consists of 11 digits
- 1st-9th digits - any digits;
- 10th-11th digits - control number.

## Checksum Check Algorithm
1. Calculate the sum of the products of the SNILS digits (from 1st to 9th) by the following coefficients - 9, 8, 7, 6, 5, 4, 3, 2, 1 (i.e. the numbers of the digits in reverse order).
2. Calculate the check number from the received amount as follows:
   1. if it is less than 100, then the control number is equal to this sum;
   2. if equal to 100, then the control number is equal to 0;
   3. if greater than 100, then calculate the remainder after dividing by 101 and beyond: 
      1. if the remainder of the division is 100, then the control number is 0;
      2. otherwise, the checksum is equal to the calculated remainder of the division.
3. Compare the resulting check number with the two least significant digits of SNILS. If they are equal, then SNILS is correct.
