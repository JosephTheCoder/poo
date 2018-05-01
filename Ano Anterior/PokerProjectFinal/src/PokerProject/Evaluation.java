package PokerProject;

final class Evaluation {	
	
	private Evaluation() {} // No instances
	
	// Size of lookup array equal to all combos of up to 5 of LS 13 bits on (1111100000000) 
	private static final int   ARRAY_SIZE		= 0x1F00 + 1;
	// Lookup array with the count of bits set in each mask
	private static final int[] nbrOfRanks		= new int[ARRAY_SIZE];		

	/* Static initialiser, the first to be executed when the class is loaded, creates the array 
	 * with the count of bits set in the mask
	 */
	static{
		int mask, bitCount;
		int shiftReg, i;

		for (mask = 1; mask < ARRAY_SIZE; ++mask) {
			bitCount = 0;
			shiftReg = mask;
			for (i = 12; i >= 0; --i, shiftReg <<= 1){
				if ((shiftReg & 0x1000) != 0)	
					++bitCount;
			}
			nbrOfRanks[mask] = bitCount;
			
			bitCount = 0;
			shiftReg = mask;			
		}
	}
	
	
	/**
	 * Returns a value which can be used in building a parameter to one of the 
	 * HandEval evaluation methods.
	 * @param card a {@link Card}.
	 * @return a value which may be bitwise OR'ed or added to other values to 
	 * build a parameter to advice method.
	 */	
	public static long encode(final Card card) {
		//System.out.println("SuitIndex = "+(card.getSuit()+1)+" Value = "+(card.getValue()+1));
		//System.out.println(card);
		return 0x1L << ((card.getSuit())*16 + (card.getValue()));
	}
	
	/**
	 * Returns a value which can be used as a parameter to the advice method.
	 * @param hand a {@link Card} array.
	 * @return a value which can be used as a parameter to the advice methods.
	 * The value may also be bitwise OR'ed or added to other  values to build
	 *  an advice method parameter.
	 */
	public static long encode(final Card[] hand) {
		long result = 0;
		for (Card c : hand)
			result = result | encode(c);
		return result;
	}
	
	/**
	 * Returns the number of bits set in mask.
	 * @param mask an integer in the range 0..0x1F00 (7936).
	 * @return the number of bits set in mask.
	 * @throws IndexOutOfBoundsException if mask less than 0 or higher than 7936.
	 */
	public static int numberOfRanks(int mask){
	    return nbrOfRanks[mask];
	}	
	
	/**
	 * Returns the position in the hand array (1..5) of a card with a specific 
	 * value (0..12) and a specific suit (0..3).
	 * @param hand a {@link Card} array.
	 * @param cardValue	an integer in the range 0..12
	 * @param cardSuit 	an integer in the range 0..3
	 * @return position 	in the hand array (1,..,5) of a specific card or 
	 * -1 if the card was not found.
	 */
	public static int getPosCard(Card[] hand, int cardValue, int cardSuit){
		int i=1; // Because the cards to hold start in 1..5
		for(Card c : hand){
			if(c.getSuit() == cardSuit && c.getValue() == cardValue){
				return i;
			}
			i++;
		}		
		return -1;
	}
	
	
	/**
	 * Returns an array that contains the index of cards to hold by the 
	 * user according to the strategy to win a hand 
	 * @param userHand a {@link Card} array.
	 * @return holdVec an array that contains the index of cards to hold.
	 */
	public static int[] advice(Card[] userHand) {
		
		long hand = encode(userHand);
		
		// Masks
		int mask, mask2;
		int royalflushMask, fourkindMask, threekindMask, twopairMask, highcardMask;
		
		// Return vector
		int[] holdVec;
		
		// Define the suits masks
		final int[] suit = {(int)hand & 0x1FFF,
							(int)(hand >>> 16) & 0x1FFF,
							(int)(hand >>> 32) & 0x1FFF,
							(int)(hand >>> 48) & 0x1FFF};
		// Suit index (0 - Clubs; 1 - Diamonds; 2 - Hearts; 3 - Spades
		int suitNb = 0;			
				
		// Sum of all suits mask bits
		final int ranks = suit[0] | suit[1] | suit[2] | suit[3];
		
		// Count variables
		int i, x, n;
		
		//System.out.println("ranks="+Long.toBinaryString(ranks));
		
		/*
		 * Checking the strategy from the best to the worst */
		 
		// Play: Royal flush
		royalflushMask = 0x1E01; // Royal Flush Mask
		if (numberOfRanks(suit[0]) == 5 || numberOfRanks(suit[1]) == 5 || numberOfRanks(suit[2]) == 5 | numberOfRanks(suit[3]) == 5){ // If all cards are from the same suit
			mask = ranks ^ royalflushMask; 
			if(mask == 0){
				holdVec = new int[5];
				holdVec[0] = 1;
				holdVec[1] = 2;
				holdVec[2] = 3;
				holdVec[3] = 4;
				holdVec[4] = 5;				
				System.out.println("Existe Royal Flush!");
				return holdVec;
			}
		}
		
		// Play: 4 of a kind
		fourkindMask = (suit[0] & suit[1]) & (suit[2] & suit[3]);
		if (fourkindMask != 0){
			holdVec = new int[4];
			for (mask = 1, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){				
				if ((ranks & fourkindMask) == mask){			
					holdVec[0] = getPosCard(userHand, x, 0);
					holdVec[1] = getPosCard(userHand, x, 1);
					holdVec[2] = getPosCard(userHand, x, 2);
					holdVec[3] = getPosCard(userHand, x, 3);	
					break;
				}
			}			
			System.out.println("Existe 4 of a kind!");
			return holdVec;
		}
		
		// Play: Straight Flush
		if (numberOfRanks(suit[0]) == 5 || numberOfRanks(suit[1]) == 5 || numberOfRanks(suit[2]) == 5 | numberOfRanks(suit[3]) == 5){	
			for (mask = 0x1F, x = 0; mask < 0x1F00; mask = mask << 1, x++){ // Mask = 11111
				if ((ranks & mask) == mask){
					holdVec = new int[5];
					holdVec[0] = 1;
					holdVec[1] = 2;
					holdVec[2] = 3;
					holdVec[3] = 4;
					holdVec[4] = 5;
					System.out.println("Existe straight flush!");
					return holdVec;
				}
			}
		}
		
		// Play: 4 to a Royal Flush		
		for (suitNb = 0; suitNb < 4; suitNb++){
			if (numberOfRanks(suit[suitNb]) >= 4){
				n = 0;
				holdVec = new int[4];
				for (mask = 0x200, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){ // Mask = 1000000000, bits will be set only to J..K
					if ((suit[suitNb] & mask) == mask){					
						holdVec[n] = getPosCard(userHand, 9+x, suitNb);
						n++;
					}
				}
				if ((suit[suitNb] & 1) == 1){ // In case Ace is part of the hand
					holdVec[n] = getPosCard(userHand, 0, suitNb) ;
					n++;
				}
				if (n == 4){
					System.out.println("Existe 4 to a royal flush");
					return holdVec;
				}								
			}
		}
		
		// Play: 3 Aces
		threekindMask = (( suit[0]&suit[1] )|( suit[2]&suit[3] )) & (( suit[0]&suit[2] )|( suit[1]&suit[3] ));;
		if ((threekindMask != 0) && ((threekindMask & 1) == 1)){
			n = 0;
			holdVec = new int[3];
			for (suitNb = 0; suitNb<4; suitNb++){
				if ((suit[suitNb] & 1) == 1){					
					holdVec[n] = getPosCard(userHand, 0, suitNb);
					n++;
				}
			}
			System.out.println("Existe 3 Aces");
			return holdVec;
		}
			
		// Play: Full House		
		twopairMask = ranks ^ (suit[0] ^ suit[1] ^ suit[2] ^ suit[3]); // Two pair mask
		if (numberOfRanks(ranks) == 2){	// If only exists 2 different values		
			// If numberOfRanks(twopairMask) equals to one, there is a pair, therefore there is also a 3 of a kind
			if (numberOfRanks(twopairMask) == 1){  
				holdVec = new int[5];
				holdVec[0] = 1;
				holdVec[1] = 2;
				holdVec[2] = 3;
				holdVec[3] = 4;
				holdVec[4] = 5;
				System.out.println("Existe full house! ");
				return holdVec;
			}
		}
		
		// Play: Flush
		if (numberOfRanks(suit[0]) == 5 || numberOfRanks(suit[1]) == 5 || numberOfRanks(suit[2]) == 5 | numberOfRanks(suit[3]) == 5){
			holdVec = new int[5];
			holdVec[0] = 1;
			holdVec[1] = 2;
			holdVec[2] = 3;
			holdVec[3] = 4;
			holdVec[4] = 5;
			System.out.println("Existe flush!");
			return holdVec;
		}
		
		// Play: Straight
		for (mask = 0x1F; mask <= 0x1F00; mask = mask << 1){
			if ((ranks & mask) == mask || (ranks & 0x1E01) == 0x1E01){
				holdVec = new int[5];
				holdVec[0] = 1;
				holdVec[1] = 2;
				holdVec[2] = 3;
				holdVec[3] = 4;
				holdVec[4] = 5;
				System.out.println("Existe straight!");
				return holdVec;
			}
		}
		

		// Play: 3 of a Kind		
		threekindMask = (( suit[0]&suit[1] )|( suit[2]&suit[3] )) & (( suit[0]&suit[2] )|( suit[1]&suit[3] ));
		if (threekindMask != 0){ // If exists a 3 of a kind
			n = 0;
			holdVec = new int[3];
			for (suitNb = 0; suitNb < 4; suitNb++){
				for (mask = 1, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){				
					if ((suit[suitNb] & threekindMask) == mask){			
						holdVec[n] = getPosCard(userHand, x , suitNb);
						n++;
					}
				}			
			}
			System.out.println("Existe three of a kind! ");
			return holdVec;
		}
		
		
		// Play: 4 to a Straight Flush		
		for (suitNb = 0; suitNb < 4; suitNb++){
			if (numberOfRanks(suit[suitNb]) >= 4){ // One to flush at least
				n = 0;		
				holdVec = new int[4];	
				for (mask = 0x1F, x = 0; mask <= 0x1F00; mask = mask << 1, x++){
					if(numberOfRanks(mask & suit[suitNb]) == 4){	
						for (mask2 = 1 << x, i = x; mask2 <= 0x1000; mask2 = mask2 << 1, i++){	
							if ((suit[suitNb] & mask & mask2) == mask2){							
								holdVec[n] = getPosCard(userHand, i , suitNb);
								n++;
							}				
							if (n == 4){
								System.out.println("Existe 4 to straight flush");
								return holdVec;
							}
						}
					}
				}					
			}
		}
		
		// Play: Two Pair
		twopairMask = ranks ^ (suit[0] ^ suit[1] ^ suit[2] ^ suit[3]);
		if (numberOfRanks(ranks) == 3){	// If exists only 3 different values (2 of the pairs and other)		
			if (numberOfRanks(twopairMask) == 2){
				n = 0;
				holdVec = new int[4];
				for (suitNb = 0; suitNb < 4; suitNb++){
					for (mask = 1, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){				
						if ((suit[suitNb] & mask & twopairMask) == mask){									
							holdVec[n] = getPosCard(userHand, x , suitNb);
							n++;							
						}
					}
					if(n == 4){
						System.out.println("Existe two pair");
						return holdVec;
					}
				}								
			}
		}
		

		// Play: High pair
		highcardMask = 0x1C01;
		mask = twopairMask & highcardMask; // Mask for high card pairs
		if(numberOfRanks(mask) == 1){	
			n = 0;
			holdVec = new int[2];
			for(suitNb = 0; suitNb<4; suitNb++){
				for(mask2 = 1, x = 0; mask2 <= 0x1000 ; mask2 = mask2 << 1, x++){				
					if((suit[suitNb] & mask) == mask2){			
						holdVec[n] = getPosCard(userHand, x , suitNb);
						n++;
					}
				}			
			}
			System.out.println("Existe high pair");
			return holdVec;
		}
		
		// Play: 4 to a Flush
		for(suitNb = 0; suitNb<4; suitNb++){
			if(numberOfRanks(suit[suitNb]) >= 4){
				n = 0;
				holdVec = new int[4];
				for(mask = 1, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){
					if((suit[suitNb] & mask) == mask){
						holdVec[n] = getPosCard(userHand, x , suitNb);
						n++;
					}
				}
				System.out.println("Existe 4 to a flush");
				return holdVec;
			}
		}
				
		// Play: 3 to a Royal Flush
		if(numberOfRanks(ranks & 0x1E01) >= 3){
			n = 0;
			holdVec = new int[3];
			for(suitNb = 0; suitNb < 4; suitNb++){										
				for(mask = 0x200, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){
					if((suit[suitNb] & mask) == mask){					
						holdVec[n] = getPosCard(userHand, 9+x, suitNb);
						n++;
					}
				}
				if((suit[suitNb] & 1) == 1){
					holdVec[n] = getPosCard(userHand, 0, suitNb) ;
					n++;
				}
				if(n == 3){
					System.out.println("Existe 3 to a royal flush");
					return holdVec;
				}	
				n = 0;
			}
		}
		
		// Play: 4 to an outside Straight	
		System.out.println("rank="+Long.toBinaryString(ranks));
		for (mask = 0x1E, x = 0; mask <= 0xF00; mask = mask << 1, x++){			
			if ((ranks & mask) == mask){				
				n = 0;
				holdVec = new int[4];				
				for(suitNb = 0; suitNb<4; suitNb++){					
					for(mask2 = 1 << x+1, i = x+1; mask2 <= (1 << x+3) ; mask2 = mask2 << 1, i++){						
						if((suit[suitNb] & mask & mask2) == mask2){							
							holdVec[n] = getPosCard(userHand, i , suitNb);
							n++;
						}
					}
					if (n == 4){
						System.out.println("4 to an outside straight!");
						return holdVec;
					}
				}
				
			}
		}
		
		// Play: Low Pair
		twopairMask = ranks ^ (suit[0] ^ suit[1] ^ suit[2] ^ suit[3]);
		twopairMask = twopairMask & 0x3FE;		
		if(numberOfRanks(twopairMask) == 1){	
			n = 0;
			holdVec = new int[2];
			for(suitNb = 0; suitNb<4; suitNb++){
				for(mask = 1, x = 0; mask <= 0x200 ; mask = mask << 1, x++){				
					if((suit[suitNb] & twopairMask) == mask){			
						holdVec[n] = getPosCard(userHand, x , suitNb);
						n++;
					}
				}			
			}
			System.out.println("Existe low pair");
			return holdVec;
		}
		
		// Play: AKQJ unsuited	
		if((ranks & 0x1C01) == 0x1C01){
			n = 0;
			holdVec = new int[4];
			for(suitNb = 0; suitNb<4; suitNb++){	
				for(mask = 1, x = 0; mask <= 0x1000 ; mask = mask << 1, x++){	
					if((suit[suitNb] & 0x1C01 & mask) == mask){
						holdVec[n] = getPosCard(userHand, x , suitNb);
						n++;
					}
				}
			}
			System.out.println("Existe AKQJ unsuited!");
			return holdVec;
		}
		
		// Play: Checks 3 to a straight flush (type 1) - FALTA CONFIRMAR 234 E ace low		
		if (numberOfRanks(ranks & 0x1C01) >= 1){ // Checks exists at least one high card, since the nº gaps >=1 always
			for(suitNb = 0; suitNb<4; suitNb++){
				if(numberOfRanks(suit[suitNb]) >= 3){
					for (mask = 0x1F << 4, x = 5; mask <= 0x1F00; mask = mask <<1, x++){						
						if(numberOfRanks(suit[suitNb] & mask) >=3){									
							mask = mask & suit[suitNb];						
							if ((mask & (1 << x-1)) == 0){ // Sequence starts without that card -> one gap
								if(numberOfRanks(suit[suitNb] & 0x1C01) >= 1){ //AQUI
									n = 0;
									holdVec = new int[3];	
									for (mask2 = 1 << x, i = x; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
										if((mask2 & mask) == mask2){
											holdVec[n] = getPosCard(userHand, i , suitNb);
											n++;
										}
									}
									if(n == 3){										
										System.out.println("Existe 3 to a straight flush (type 1)");
										return holdVec;
									}
								}
							} else {
								if(numberOfRanks(suit[suitNb] & 0x1C01) >= 2){
									n = 0;
									holdVec = new int[3];		
									for (mask2 = 1 << x-1, i = x - 1; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
										if((mask2 & mask) == mask2){
											holdVec[n] = getPosCard(userHand, i , suitNb);
											n++;
										}
									}
									if(n == 3){										
										System.out.println("Existe 3 to a straight flush (type 1)");
										return holdVec;
									}
								}
							}
						}
					}								
				}
			}
		}						
		
		// Play: 4 to an inside Straight with 3 high cards		
		if (numberOfRanks((ranks & 0x1C01)) == 3){ // Checks if there is 3 high cards
			for (suitNb = 0; suitNb<4; suitNb++){
				if ((ranks & 0x1100) == 0x1100){	// Checks if the extreme points are 9 and K
					n = 0;
					holdVec = new int[4];
					for (suitNb = 0; suitNb<4; suitNb++){
						for (mask = 0x100, x = 8; mask <= 0x1000; mask = mask << 1, x++){
							if ((suit[suitNb] & mask) == mask){
								holdVec[n] = getPosCard(userHand, x , suitNb);
								n++;
							}
						}
						if (n == 4){
							System.out.println("Existe 4 to an inside straight with 3 high cards");
							return holdVec;
						}
					}
				} else if ((ranks & 0x201) == 0x201){	// Checks if the extreme points are T and A
					n = 0;
					holdVec = new int[4];
					for (suitNb = 0; suitNb<4; suitNb++){
						for (mask = 0x200, x = 9; mask <= 0x1000; mask = mask << 1, x++){							
							//System.out.println("mask="+Long.toBinaryString(mask));
							if ((suit[suitNb] & mask) == mask){
								//System.out.println("Adding "+x+ " suit="+suitNb);
								holdVec[n] = getPosCard(userHand, x , suitNb);
								n++;
							}
						}						
						
						
						if ((suit[suitNb] & 1) == 1){							
							//System.out.println("Adding "+0+ " suit="+suitNb);
							holdVec[n] = getPosCard(userHand, 0 , suitNb);
							n++;
						}
						if (n == 4){
							System.out.println("Existe 4 to an inside straight with 3 high cards");
							return holdVec;
						}
					}					
				}					
			}	
		}
		
		// Play: QJ suited		
		if ((ranks & 0xC00) == 0xC00){			
			for (suitNb = 0; suitNb<4; suitNb++){	
				if (numberOfRanks(suit[suitNb] & 0xC00) == 2){
					if ((suit[suitNb] & 0xC00) == 0xC00){
						n = 0;
						holdVec = new int[2];
						holdVec[0] = getPosCard(userHand, 10 , suitNb);
						holdVec[1] = getPosCard(userHand, 11 , suitNb);
						System.out.println("Existe QJ suited!");
						return holdVec;
					} 
				}
			}
		}
		
		// 3 to a flush with 2 high cards
		if (numberOfRanks((ranks & 0x1C01)) >= 2){ // Checks if there is 2 high cards
			for (suitNb = 0; suitNb<4; suitNb++){
				if (numberOfRanks(suit[suitNb]) == 3){
					if (numberOfRanks(suit[suitNb] & 0x1C01) >= 2){ // Checks if exist at least 3 cards of same suit and 2 of them are high cards
						n = 0;
						holdVec = new int[3];
						for (mask = 1, x = 0; mask <= 0x1000; mask = mask << 1, x++){
							if ((suit[suitNb] & mask) == mask){
								holdVec[n] = getPosCard(userHand, x , suitNb);
								n++;
							}
						}
						System.out.println("3 to a flush with 2 high cards");
						return holdVec;
					}
				}
			}			
		}
		
		// 2 suited high cards		
		if (numberOfRanks((ranks & 0x1C01)) == 2){ // Checks if there is 2 high cards
			for (suitNb = 0; suitNb < 4; suitNb++){
				if (numberOfRanks(suit[suitNb]) == 2){
					n = 0;
					holdVec = new int[2];
					for (mask = 1, x = 0; mask <= 0x1000; mask = mask << 1, x++){
						if (((suit[suitNb] & 0x1C01) & mask) == mask){
							holdVec[n] = getPosCard(userHand, x , suitNb);
							n++;
						}
					}
					if (n == 2){
						System.out.println("2 suited high cards");
						return holdVec;
					}
				}
			}
		}
		
		// Play: 4 to an inside straight with 2 high cards
		if (numberOfRanks(ranks & 0x1C01) >= 2){	// Checks if exists at least 2 high card						
			for (mask = 0x1F, x = 0; mask <= 0x1F00; mask = mask << 1, x++){
				if (numberOfRanks(ranks & mask) == 4 && numberOfRanks(ranks & mask & 0x1C01) == 2){					
					n = 0;
					holdVec = new int[4];
					mask = ranks & mask;
					for (suitNb = 0; suitNb < 4; suitNb++){						
						for (mask2 = 1 << x-1, i = x - 1; mask2 <= 0x1000; mask2 = mask2 << 1, i++){
							if((suit[suitNb] & mask & mask2) == mask2){
								holdVec[n] = getPosCard(userHand, i, suitNb);
								n++;
							}
							if (n == 4){
								System.out.println("Existe 4 to an inside straight with 2 high cards!");
								return holdVec;
							}
						}						
					}					
				}
			}			
		}			
		
		// Play: 3 to a Straight Flush (type 2)
		for (suitNb = 0; suitNb < 4; suitNb++){
			if (numberOfRanks(suit[suitNb]) >= 3){ // At least 3 cards of the same suit				
				for (mask = 0x1F, x = 0; mask <= 0x1F00; mask = mask <<1, x++){
					if (numberOfRanks(suit[suitNb] & mask) >= 3){  																					
						mask = mask & suit[suitNb];
						if ((mask & (1 << x-1)) == 0 && (mask & (1 << x+4)) == (1 << x+4)){ // One gap only
							if (numberOfRanks(suit[suitNb] & 0x1C01) >= 1){ // Exists at least one high card								
								n = 0;
								holdVec = new int[3];
								for (mask2 = 1 << x, i = x; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
									if ((mask2 & mask) == mask2){
										holdVec[n] = getPosCard(userHand, i , suitNb);
										n++;
									}
								}
								if(n == 3){										
									System.out.println("Existe 3 to a straight flush (type 2)");
									return holdVec;
								}
							}
						// If the extremes of the mask don't have the bits on, we don't have gaps								
						} else if ((mask & (1 << x-1)) == 0 && (mask & (1 << x+3)) == 0){
							if (numberOfRanks(suit[suitNb] & 0xE) == 3){ // Mask for 234 = 0xE
								n = 0;
								holdVec = new int[3];		
								System.out.println("Entra 234 suited");
								for (mask2 = 1 << x-1, i = x - 1; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
									if ((mask2 & mask) == mask2){
										holdVec[n] = getPosCard(userHand, i , suitNb);
										n++;
									}
								}
								if (n == 3){										
									System.out.println("Existe 3 to a straight flush (type 2)");
									return holdVec;
								}
							}
						// If the extremes of the mask have the bits on, we have two gaps	
						} else if ((mask & (1 << x)) == (1 << x) && (mask & (1 << x+4)) == (1 << x+4)){
							if (numberOfRanks(suit[suitNb] & 0x1C01) == 1){ // Exists one high card
								n = 0;
								holdVec = new int[3];		
								System.out.println("Existe uma high card e two gapps");
								for (mask2 = 1 << x-1, i = x - 1; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
									if ((mask2 & mask) == mask2){
										holdVec[n] = getPosCard(userHand, i , suitNb);
										n++;
									}
								}
								if (n == 3){										
									System.out.println("Existe 3 to a straight flush (type 2)");
									return holdVec;
								}
							} else if ((mask & suit[suitNb] & 1) == 1){ // Exists a ace-low
								n = 0;
								holdVec = new int[3];		
								System.out.println("Existe um ace-low");
								for (mask2 = 1 << x-1, i = x - 1; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
									if((mask2 & mask) == mask2){
										holdVec[n] = getPosCard(userHand, i , suitNb);
										n++;
									}
								}
								if(n == 3){										
									System.out.println("Existe 3 to a straight flush (type 2)");
									return holdVec;
								}
							}
						}						
					}
				}								
			}
		}						
		
		
		// Play: 4 to an inside straight with 1 high cards		
		if (numberOfRanks(ranks & 0x1C01) >= 1){	// Checks if exists at least one high card						
			for (mask = 0x1F, x = 0; mask <= 0x1F00; mask = mask << 1, x++){
				if (numberOfRanks(ranks & mask) == 4 && numberOfRanks(ranks & mask & 0x1C01) == 1){ // One high card in the mask of straight
					n = 0;
					holdVec = new int[4];
					mask = ranks & mask;
					for (suitNb = 0; suitNb < 4; suitNb++){						
						for (mask2 = 1 << x, i = 0; mask2 <= 0x1000; mask2 = mask2 << 1, i++){
							if((suit[suitNb] & mask & mask2) == mask2){
								holdVec[n] = getPosCard(userHand, x + i, suitNb);
								n++;
							}
							if (n == 4){
								System.out.println("Existe 4 to an inside straight with 1 high cards!");
								return holdVec;
							}
						}						
					}					
				}
			}			
		}
		
		// Play: KQJ unsuited		
		if(numberOfRanks(ranks & 0x1C00) == 3){							
			n = 0;
			holdVec = new int[3];						
			for(suitNb = 0; suitNb < 4; suitNb++){
				for (mask = 1 << 10, x = 10; mask <= 0x1000; mask = mask << 1, x++){
					if((suit[suitNb] & mask) == mask){
						holdVec[n] = getPosCard(userHand, x , suitNb);
						n++;
					}
				}												
			}
			if (n == 3){
				System.out.println("Existe KQJ unsuited!");
				return holdVec;
			}
		}
		
		// Play: JT suited		
		if(numberOfRanks(ranks & 0x600) == 2){	// 						
			n = 0;
			holdVec = new int[2];					
			for(suitNb = 0; suitNb<4; suitNb++){
				if(numberOfRanks(suit[suitNb] & 0x600) == 2){
					holdVec[0] = getPosCard(userHand, 9 , suitNb);
					holdVec[1] = getPosCard(userHand, 10 , suitNb);
					System.out.println("Existe JT suited!");
					return holdVec;
				}							
			}
		}
		
		// Play: QJ unsuited
		if(numberOfRanks(ranks & 0xC00) == 2){							
			n = 0;
			holdVec = new int[2];						
			for(suitNb = 0; suitNb<4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x400) == 1){
					holdVec[n] = getPosCard(userHand, 10 , suitNb);
					n++;
				}
				if (numberOfRanks(suit[suitNb] & 0x800) == 1){
					holdVec[n] = getPosCard(userHand, 11 , suitNb);
					n++;
				}											
			}
			if (n == 2){
				System.out.println("Existe QJ unsuited!");
				return holdVec;
			}			
		}
		
		// Play: 3 to a flush with 1 high cards		
		if(numberOfRanks((ranks & highcardMask)) >= 1){ // Checks if there is 1 high cards
			for(suitNb = 0; suitNb < 4; suitNb++){
				if (numberOfRanks(suit[suitNb]) == 3){	// Checks if exists at least 3 cards of same suit
					if (numberOfRanks(suit[suitNb] & highcardMask) == 1){ // And one of them is a high card
						n = 0;
						holdVec = new int[3];
						for(mask = 1, x = 0; mask <= 0x1000; mask = mask << 1, x++){
							if ((suit[suitNb] & mask) == mask){
								holdVec[n] = getPosCard(userHand, x , suitNb);
								n++;
							}
						}
						System.out.println("3 to a flush with 1 high cards");
						return holdVec;
					}
				}
			}			
		}
		
		// Play: QT suited
		if(numberOfRanks(ranks & 0xA00) == 2){	// Q and T mask = 0xA00						
			n = 0;
			holdVec = new int[2];					
			for(suitNb = 0; suitNb<4; suitNb++){
				if(numberOfRanks(suit[suitNb] & 0xA00) == 2){
					holdVec[0] = getPosCard(userHand, 9 , suitNb);
					holdVec[1] = getPosCard(userHand, 11 , suitNb);
					System.out.println("Existe QT suited!");
					return holdVec;
				}							
			}
		}
		
		// Play: 3 to a straight flush with 0 high cards (type 3)	
		for(suitNb = 0; suitNb < 4; suitNb++){
			if (numberOfRanks(suit[suitNb] & 0x3FF) == 3){	
				for (mask = 0x1F, x = 0; mask <= 0x3E0; mask = mask << 1, x++){
					if (numberOfRanks(suit[suitNb] & mask) >= 3){	// Checks if exists at least 3 cards of same suit					
						mask = mask & suit[suitNb];			
						// If the extremes of the mask have the bits on, we have two gaps
						if((mask & (1 << x)) == (1 << x) && (mask & (1 << x+4)) == (1 << x+4)){
							if (numberOfRanks(suit[suitNb] & highcardMask) == 0){ // Exists 0 high cards
								n = 0;
								holdVec = new int[3];		
								for (mask2 = 1 << x-1, i = x - 1; mask2 <= 0x1000; mask2 = mask2 <<1, i++){
									if((mask2 & mask) == mask2){
										holdVec[n] = getPosCard(userHand, i , suitNb);
										n++;
									}
								}
								if(n == 3){										
									System.out.println("Existe 3 to a straight flush (type 3)");
									return holdVec;
								}
							}		
						}
					}
				}
			}
		}

		// Play: KQ unsuited		
		if(numberOfRanks(ranks & 0x1800) == 2){	// K and Q mask = 0x1800		
			n = 0;
			holdVec = new int[2];						
			for(suitNb = 0; suitNb<4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x1000) == 1){
					holdVec[n] = getPosCard(userHand, 12 , suitNb);
					n++;
				}
				if (numberOfRanks(suit[suitNb] & 0x800) == 1){
					holdVec[n] = getPosCard(userHand, 11 , suitNb);
					n++;
				}											
			}
			if (n == 2){
				System.out.println("Existe KQ unsuited!");
				return holdVec;
			}			
		}
		
		// Play: KJ unsuited		
		if (numberOfRanks(ranks & 0x1400) == 2){ // K and T mask = 0x1400
			n = 0;
			holdVec = new int[2];						
			for (suitNb = 0; suitNb < 4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x400) == 1){
					holdVec[n] = getPosCard(userHand, 10 , suitNb);
					n++;
				}
				if (numberOfRanks(suit[suitNb] & 0x1000) == 1){
					holdVec[n] = getPosCard(userHand, 12 , suitNb);
					n++;
				}											
			}
			if (n == 2){
				System.out.println("Existe KJ unsuited!");
				return holdVec;
			}			
		}
		
		// Play: Aces
		if (numberOfRanks(ranks & 1) == 1){ // Aces mask = 1								
			holdVec = new int[1];						
			for (suitNb = 0; suitNb < 4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 1) == 1){
					holdVec[0] = getPosCard(userHand, 0 , suitNb);
					System.out.println("Existe Ace!");
					return holdVec;
				}								
			}		
		}
		
		// Play: KT suited
		if (numberOfRanks(ranks & 0x1200) == 2){	// K and T mask = 0x1200				
			holdVec = new int[2];					
			for (suitNb = 0; suitNb < 4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x1200) == 2){
					holdVec[0] = getPosCard(userHand, 9 , suitNb);
					holdVec[1] = getPosCard(userHand, 12 , suitNb);
					System.out.println("Existe KT suited!");
					return holdVec;
				}							
			}
		}
		
		// Play: Kings
		if(numberOfRanks(ranks & 0x1000) == 1){	// Kings mask = 0x800						
			holdVec = new int[1];						
			for(suitNb = 0; suitNb<4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x1000) == 1){
					holdVec[0] = getPosCard(userHand, 12 , suitNb);
					System.out.println("Existe King!");
					return holdVec;
				}								
			}		
		}
		
		// Play: Queen
		if(numberOfRanks(ranks & 0x800) == 1){ // Queens mask = 0x800								
			holdVec = new int[1];						
			for(suitNb = 0; suitNb<4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x800) == 1){
					holdVec[0] = getPosCard(userHand, 11 , suitNb);
					System.out.println("Existe Queen!");
					return holdVec;
				}								
			}		
		}
		
		// Play: Jack
		if(numberOfRanks(ranks & 0x400) == 1){ // Jack mask = 0x400						
			holdVec = new int[1];						
			for(suitNb = 0; suitNb<4; suitNb++){
				if (numberOfRanks(suit[suitNb] & 0x400) == 1){
					holdVec[0] = getPosCard(userHand, 10 , suitNb);
					System.out.println("Existe Jacks!");
					return holdVec;
				}								
			}		
		}
		
		
		// 4 to an inside straight with 0 high cards		
		if (numberOfRanks(ranks & highcardMask) == 0){	// No high cards in the hand
			for (mask = 0x1F, x = 0; mask <= 0x1F00; mask = mask << 1, x++){
				if (numberOfRanks(ranks & mask) == 4 && numberOfRanks(ranks & mask & highcardMask) == 0){ 
					n = 0;
					holdVec = new int[4];
					mask = ranks & mask;
					for (suitNb = 0; suitNb < 4; suitNb++){						
						for (mask2 = 1 << x, i = 0; mask2 <= 0x200; mask2 = mask2 << 1, i++){
							if((suit[suitNb] & mask & mask2) == mask2){
								holdVec[n] = getPosCard(userHand, x + i, suitNb);
								n++;
							}
						}	
						if (n == 4){
							System.out.println("Existe 4 to an inside straight with 0 high cards!");
							return holdVec;
						}
					}					
				}
			}			
		}
		
		// Play: 3 to a flush with 0 high cards		
		if(numberOfRanks((ranks & highcardMask)) == 0){ // No high cards in the hand
			for(suitNb = 0; suitNb < 4; suitNb++){
				if (numberOfRanks(suit[suitNb]) == 3){	
					if (numberOfRanks(suit[suitNb] & highcardMask) == 1){ // Must exist at least 3 cards of same suit
						n = 0;
						holdVec = new int[3];
						for(mask = 1, x = 0; mask <= 0x200; mask = mask << 1, x++){
							if ((suit[suitNb] & mask) == mask){
								holdVec[n] = getPosCard(userHand, x , suitNb);
								n++;
							}
						}
						System.out.println("3 to a flush with 0 high cards");
						return holdVec;
					}
				}
			}			
		}		
		
		System.out.println("Discards everything!");
		return new int[0];
	
	}

	
		
	public static void main(String[] args){
		Game gameplay = new Interactive(50);
		
		String cartasDeck = "JC QH AS TD 9S";
		char[] cartasDeckchar = cartasDeck.toCharArray();
		gameplay.deck = new Deck(cartasDeckchar);
		gameplay.user.newHand(gameplay.deck);
		System.out.println("player's hand " + gameplay.user.getHandStr());
		
		long sthand = encode(gameplay.user.getHand());

		int[] holdVec;		
		
		holdVec = advice(gameplay.user.getHand());
		
		
		/************************/
		if(false){
			String[] str = new String[82];
			str[0] = "7D 5S TD 9D 8D";
			// Difficult hands
			str[1] = "KC QC JC TC 9C";
			str[2] = "AD KD QS JD TD";
			str[3] = "AS KS JS TS 9S";
			str[4] = "AH AD AS 2C 2S";
			str[5] = "4C 4S 4H 5D 5C";
			str[6] = "5S 6S 7S 8S JS";
			str[7] = "3D 4H 5H 6H 7H";
			str[8] = "AC KD QD JD TS";
			str[9] = "KC QC JC 9C 4D";
			str[10] = "AH AS KD KS QS";
			str[11] = "JC JD 4D 7D 9D";
			str[12] = "QS QH JH AH 2C";
			str[13] = "8C JC QC KC 9H";
			str[14] = "2S 5S 7S 9S 7H";
			str[15] = "TD JD QC KD 5S";
			str[16] = "TH QH AH TD 8C";
			str[17] = "7C 7D 8H 9S TD";
			str[18] = "7D 8D 9D TS 4H";
			str[19] = "7C 7D 8D 9D 3S";
			str[20] = "KS QD JC 9H 9C";
			str[21] = "AC KD QH JH 8H";
			str[22] = "AC KD QH JH 9S";
			str[23] = "AC KD QH JH 2C";
			str[24] = "2H QS JS 9D 8S";
			str[25] = "8H TH JH 3S 5D";
			str[26] = "KS QD JD 9H 7C";
			str[27] = "AH KC QC TD 6C";
			str[28] = "KH QS JS 9D 3C";
			str[29] = "KC QH JS 9H 8H";
			str[30] = "QD JD 7D 5H 4S";
			str[31] = "8S 9H JD QD 2C";
			str[32] = "QS JS 2H 3H 4H";
			str[33] = "KH JH 3H 4S 6C";
			str[34] = "8S 9C JD QD 4D";
			str[35] = "2C 3D 5H JC AC";
			str[36] = "KH JH TS 9D 6C";
			str[37] = "AC JC 2S 3S 5S";
			str[38] = "8D 9D JH QD 4C";
			str[39] = "8S 9D JD QH 3D";
			str[40] = "8C 9C TD QC 2H";
			str[41] = "7C 8H TD JD 4S";
			str[42] = "7D 8C 9S JD 2D";
			str[43] = "AD 2C 4C 5C 7S";
			str[44] = "QH JS TS 2D 4C";
			str[45] = "JH TH 6H 7S 2D";
			str[46] = "JD TD 2C 4C 6C";
			str[47] = "JS TS KC 3D 7H";
			str[48] = "JC TC AH 4D 6S";
			str[49] = "6S 7C 8D TH JH";
			str[50] = "JD TD 2C 5C 7C";
			str[51] = "QC JH 9H 4H 2S";
			str[52] = "QC JD TC 3S 5H";
			str[53] = "QS JD TC 3S 5H";
			str[54] = "AD QC JS 4H 7S";
			str[55] = "QC JH 2D 5D 7D";
			str[56] = "7C TC QC 3H 2D";
			str[57] = "KS QH 8H 5H 2C";
			str[58] = "AD 5D 9D 8S 6H";
			str[59] = "7C TC KC 3H 2D";
			str[60] = "6C 7S 9C TH KC";
			str[61] = "QH TH 2C 4C 6C";
			str[62] = "KH QS TS 4C 5D";
			str[63] = "AC QD TD 6S 9H";
			str[64] = "QD TD 8H 7C 6S";
			str[65] = "QS TS 3H 7H 8H";
			str[66] = "5C 6C 9C KD QH";
			str[67] = "3H 5H 7H JS 8D";
			str[68] = "2C 3C 6C TD KD";
			str[69] = "2D 3D 5H 6D 9S";
			str[70] = "AD KC JS 7H 4C";
			str[71] = "KC QH TC 4D 6S";
			str[72] = "KH JS 3D 8D 9D";
			str[73] = "AC KH TH 4S 5D";
			str[74] = "AC JH 5S 8D 9C";
			str[75] = "AD 5H 6S 7H 9D";
			str[76] = "AS 2C 5C 9C 6H";
			str[77] = "KD 6C 7S 9H TD";
			str[78] = "KC TC 3H 5H 8H";
			str[79] = "JH 2S 3C 4D 6H";
			str[80] = "QC 2H 5H 7H 9S";
			str[81] = "2D 3S 5C 6C TC";
			
			for(int test = 1; test < str.length; test++){
				
				// CREATE HAND (FIVE CARDS)
				Card[] auxCards = new Card[5];
				String auxStr = str[test];
				char[] auxChar = auxStr.toCharArray();
				
				auxCards[0] = new Card(String.valueOf(auxChar[0]),String.valueOf(auxChar[1]));
				auxCards[1] = new Card(String.valueOf(auxChar[3]),String.valueOf(auxChar[4]));
				auxCards[2] = new Card(String.valueOf(auxChar[6]),String.valueOf(auxChar[7]));
				auxCards[3] = new Card(String.valueOf(auxChar[9]),String.valueOf(auxChar[10]));
				auxCards[4] = new Card(String.valueOf(auxChar[12]),String.valueOf(auxChar[13]));
				
				String strAux = String.valueOf(test) + " --> ";
				for(int k = 0; k < auxCards.length; k++){
					strAux = strAux + auxCards[k].toString() + " ";				
				}
				System.out.println(strAux.substring(0, strAux.length()-1));
				cartasDeckchar = str[test].toCharArray();
				gameplay.deck = new Deck(cartasDeckchar);
				gameplay.user.newHand(gameplay.deck);
				
				// GET ADVICE VERIFICATION
				DoubleBonus auxDB = new DoubleBonus();
				
				sthand = encode(gameplay.user.getHand());
				int[] holdAdvice = advice(gameplay.user.getHand());
				
				System.out.print("player should hold cards");
				for (int index = 0; index < holdAdvice.length; index++){
					System.out.print(" " + holdAdvice[index]);
				}
				System.out.println("\n");
				
			}
		}
		
		
		/***************/
		
		System.out.println("");
		for(int i = 0; i<holdVec.length; i++){
			System.out.println("HoldVec["+i+"]="+holdVec[i]);
		}
	}
	
	

}
