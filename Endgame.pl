:- include("C:/Users/nadeen/Desktop/KB2.pl").

ironman((X,Y),L,result(A,S)):- 
A = snap,ironman((X,Y),L,S)
;thanos(X,Y),grid(Q,_), M is X+1,M<Q, A= up,ironman((M,Y),L,S)
;thanos(X,Y),M is X-1, M>=0,A = down,ironman((M,Y),L,S) 
; thanos(X,Y),M is Y-1,M>=0,A= right,ironman((X,M),L,S) 
; thanos(X,Y),grid(_,O),M is Y+1,M<O,A = left,ironman((X,M),L,S).

ironman((X,Y),L,result(A,S)):- 
member((X,Y) ,L),delete(L,(X,Y),Z), A = collect,ironman((X,Y),Z,S) 
; grid(Q,_), M is X+1,M<Q, A= up, thanos(W,F), M \= W, Y \= F ,ironman((M,Y),L,S)
;M is X-1, M>=0,A = down,thanos(W,F), M \= W, Y \= F ,ironman((M,Y),L,S) 
; M is Y-1,M>=0,A= right,thanos(W,F), X \= W, M \= F ,ironman((X,M),L,S) 
; grid(_,O),M is Y+1,M<O,A = left,thanos(W,F), X \= W, M \= F,ironman((X,M),L,S).

snapped(S):- 
stones(X),
thanos(A,B),
call_with_depth_limit(ironman((A,B),X,S),12,R).


