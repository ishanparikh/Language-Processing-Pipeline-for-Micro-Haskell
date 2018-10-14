
// File:   MH_Lexer.java
// Date:   October 2013, modified October 2016.

// Java template file for lexer component of Informatics 2A Assignment 1.
// Concerns lexical classes and lexer for the language MH (`Micro-Haskell').


import java.io.* ;

class MH_Lexer extends GenLexer implements LEX_TOKEN_STREAM {

static class VarAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "VAR" ;};
    public int numberOfStates() {return 3 ;};
    
    int nextState (int state, char c) {
    switch (state) {
    case 0: if (CharTypes.isSmall(c)) return 1 ; else return 2 ;
    case 1: if (CharTypes.isSmall(c) || CharTypes.isLarge(c) || CharTypes.isDigit(c) || c== '\'')
            return 1 ; else return 2 ;
    default: return 2 ; //garbage state
    }
    }
    
    boolean accepting (int state) { return (state == 1) ;}
    int deadState() {return 2 ;}
}

static class NumAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "NUM" ;};
    public int numberOfStates() {return 4 ;};
    
    int nextState (int state, char c) {
    switch (state) {
    case 0: if (c == '0') return 1 ;
            else if (CharTypes.isDigit(c) && c!= '0') return 2 ;
            else return 3 ;
    case 1: return 3 ;                                                 //09 is not a valid input, nor is 0a
        case 2: if (CharTypes.isDigit(c)) return 2 ; else return 3 ;   //19 is a valid input. 1a isn't.
    default: return 3;
    }
    }
    
    boolean accepting (int state) { return (state == 1 || state == 2) ;}
    int deadState() { return 3 ;}
    
}

static class BooleanAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "BOOLEAN" ;};
    public int numberOfStates() {return 10 ;};
    
    int nextState (int state, char c) {
    switch(state) {
        case 0: if (c == 'T') return 1 ;
                if (c == 'F') return 5 ;
        case 1: if (c == 'r') return 2 ; else return 9 ;
        case 2: if (c == 'u') return 3 ; else return 9 ;
        case 3: if (c == 'e') return 4 ; else return 9 ;
        case 5: if (c == 'a') return 6 ; else return 9 ;
        case 6: if (c == 'l') return 7 ; else return 9 ;
        case 7: if (c == 's') return 8 ; else return 9 ;
        case 8: if (c == 'e') return 4 ; else return 9 ;
        default: return 9;
    }
    }
    
    boolean accepting (int state) { return (state == 4) ; }
    int deadState() { return 9 ;}
}

static class SymAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "SYM" ;}
    public int numberOfStates() { return 3 ;}
    
    int nextState (int state, char c) {
    switch(state) {
    case 0: if(CharTypes.isSymbolic(c)) return 1 ; else return 2 ;
    case 1: if(CharTypes.isSymbolic(c)) return 1 ;
    default: return 2 ;
    }
    }
    
    boolean accepting (int state) { return (state == 1) ; }
    int deadState() { return 2 ; }
}

static class WhitespaceAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "" ;}
    public int numberOfStates() {return 3 ;}
    
    int nextState (int state, char c) {
    switch(state) {
    case 0: if(CharTypes.isWhitespace(c)) return 1 ; else return 2 ;
    case 1: if(CharTypes.isWhitespace(c)) return 1 ;
    default: return 2 ;
    }
    }
    
    boolean accepting (int state) { return (state == 1) ; }
    int deadState() { return 2 ; }
}

static class CommentAcceptor extends GenAcceptor implements DFA {
    public String lexClass() {return "" ;}
    public int numberOfStates() {return 5 ;}
    
    int nextState (int state, char c) {
    switch(state) {
    case 0: if(c == '-') return 1 ; else return 4 ;
    case 1: if(c == '-') return 2 ; else return 4 ;
    case 2: if(c == '-') return 2 ;
            else if(!(CharTypes.isSymbolic(c)) && !(CharTypes.isNewline(c))) return 3 ;
            else return 4 ;
    case 3: if(!(CharTypes.isNewline(c))) return 3 ; else return 4 ;
    default: return 4 ;
    }
    }
    
    boolean accepting (int state) { return (state == 2 || state == 3) ; }
    int deadState() { return 4 ; }
}

static class TokAcceptor extends GenAcceptor implements DFA {

    String tok ;
    int tokLen ;
    TokAcceptor (String tok) {this.tok = tok ; tokLen = tok.length() ;}
    
    public String lexClass() { return tok ; }
    public int numberOfStates () { return (tokLen+2) ; }
    
    
    int nextState (int state, char c) {
    int[] states = new int[tokLen+2] ;
    for (int i=0; i<tokLen+2; i++) {
            states[i] = i ;
        }
    for (int i = 0; i <tokLen; i++) {
        if(state==states[i]){
            if (c == tok.charAt(i)) return states[i+1] ;  else return tokLen+1 ;
        }
        }
        return tokLen+1;
    }
    
    boolean accepting (int state) { return (state == tokLen) ; }
    int deadState() { return (tokLen + 1) ; }
}

    static DFA varAcc = new VarAcceptor() ;
    static DFA numAcc = new NumAcceptor() ;
    static DFA booleanAcc = new BooleanAcceptor() ;
    static DFA symAcc = new SymAcceptor() ;
    static DFA whitespaceAcc = new WhitespaceAcceptor() ;
    static DFA commentAcc = new CommentAcceptor() ;
    static DFA intAcc = new TokAcceptor("Integer");
    static DFA boolAcc = new TokAcceptor("Bool");
    static DFA ifAcc = new TokAcceptor("if");
    static DFA thenAcc = new TokAcceptor("then");
    static DFA elseAcc = new TokAcceptor("else");
    static DFA openAcc = new TokAcceptor("(");
    static DFA closeAcc = new TokAcceptor(")");
    static DFA scAcc = new TokAcceptor(";");
    
    static DFA[] MH_acceptors =
    new DFA[] {symAcc, whitespaceAcc, scAcc, openAcc, closeAcc, intAcc, boolAcc, ifAcc, thenAcc, elseAcc, varAcc, numAcc, booleanAcc, commentAcc  } ;

    MH_Lexer (Reader reader) {
	super(reader,MH_acceptors) ;
    }

}



