/*
 * HashValue.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef HASHVALUE_H_
#define HASHVALUE_H_

#include <iostream>

template<int L>
class HashValue {
private:
	unsigned char data[L];
public:
	HashValue(){
		for(int i=0; i< L;i++)
			data[i] =0;
	}
	virtual ~HashValue(){
	}

	unsigned char& operator[](int x){
		return data[x];
	}

	unsigned char at(int x) const{
		return data[x];
	}

	void operator^=(const HashValue<L>& value){
		for(int i =0; i< L; i++){
			data[i] ^= value.data[i];
		}
	}

	HashValue<L> operator=(unsigned char value){
		for(int i =0; i < L;i++){
			data[i] = value;
		}

		return *this;
	}
};

template<int L>
std::ostream& operator<<(std::ostream & os,const HashValue<L>& hash){
	for(int i =L-1; i>=0;i--){
		for(int j=7;j>=0;j--){
			if((hash.at(i)>>j)&1){
				os << '1';
			}
			else
				os << '0';
		}
	}

	return os;
}

#endif /* HASHVALUE_H_ */
