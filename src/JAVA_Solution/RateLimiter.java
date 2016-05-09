package JAVA_Solution;

import java.util.*;
class RateLimiter{
   public static void main(String[] args) {
       for(double time: request){
           System.out.println(time+""+limiter(time));
       }
       
   }
   static Deque<Double> q  = new LinkedList<>();
   public static boolean limiter(double time){
       if(q.size()<5||(time-q.peekFirst())>1.0){
          q.add(time);
          if(q.size()>5)
            q.pollFirst();
          return true;
       }else{
          return false;
       }
   }
       
 }
