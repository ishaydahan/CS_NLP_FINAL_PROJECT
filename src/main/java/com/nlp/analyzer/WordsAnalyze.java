//package com.nlp.analyzer;
//
//import java.util.HashSet;
//import java.util.Iterator;
//
//public class WordsAnalyze {
//	
//    public HashSet<StringM> wrong = new HashSet<StringM>();
//    public HashSet<StringM> good = new HashSet<StringM>();
//    public HashSet<StringM> deletedWrong = new HashSet<StringM>();
//    public HashSet<StringM> deletedGood = new HashSet<StringM>();
//
//	public void learningCheck(String st, boolean grade) {		
//		StringM string = new StringM(st);
//		while(string.underlyingString.length() != 0 ) {
//			int i = string.underlyingString.indexOf(" ");
//			if(i != -1) {
//				String t = string.underlyingString.substring(0, i);
//				StringM temp = new StringM(t);
//				String s = string.underlyingString.substring(i+1);
//				string = new StringM(s);
//				if(grade) {
//					boolean one = wrong.contains(temp);
//					if(one) {
//						deletedWrong.add(temp);
//						wrong.remove(temp);
//					} else if(deletedWrong.contains(temp)) {
//					} else {
//						good.add(temp);						
//					}
//				} else {
//					if(good.contains(temp)) {
//						deletedGood.add(temp);
//						good.remove(temp);
//					} else if(deletedGood.contains(temp)) {
//					} else {
//						wrong.add(temp);						
//					}
//				}
//			} else {
//				if(grade) {
//					if(wrong.contains(string)) {
//						deletedWrong.add(string);
//						wrong.remove(string);
//					} else if(deletedWrong.contains(string)) {
//					} else {
//						good.add(string);						
//					}
//				} else {
//					if(good.contains(string)) {
//						deletedGood.add(string);
//						good.remove(string);
//					} else if(deletedGood.contains(string)) {
//					} else {
//						wrong.add(string);						
//					}
//				} break;
//			}
//		}
//	}
//
//
//public Boolean checkWord(String string) {
//	Iterator<StringM> itr_good = good.iterator();
//	Iterator<StringM> itr_wrong = wrong.iterator();
//	String[] arr = string.split(" ");   
//	 for ( String ss : arr) {
//		 	itr_good = good.iterator();
//			while(itr_good.hasNext()){ 
//				int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_good.next().underlyingString);
//				if (a <= 3) {
//					return true;
//				}
//			}
//	  }
//	for ( String ss : arr) {
//		itr_wrong = wrong.iterator();
//		while(itr_wrong.hasNext()){ 
//			int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_wrong.next().underlyingString);
//			if (a <= 3) {
//				return false;
//			}
//		}
//	}
//	return true;	
//}
//}
