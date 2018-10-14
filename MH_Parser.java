
// File:   MH_Parser.java
// Date:   October 2013, modified October 2016.

// Java template file for parser component of Informatics 2A Assignment 1.
// Students should add a method body for tableEntry, implementing the LL(1) parse table for Micro-Haskell.


import java.io.* ;
 
class MH_Parser extends GenParser implements PARSER {

    String startSymbol() {return "#Prog" ;}

    // Right hand sides of all productions in grammar:

    static String[] epsilon              = new String[] { } ;
    static String[] Decl_Prog            = new String[] {"#Decl", "#Prog"} ;
    static String[] TypeDecl_TermDecl    = new String[] {"#TypeDecl", "#TermDecl"} ;
    static String[] VAR_has_Type         = new String[] {"VAR", "::", "#Type", ";"} ;
    static String[] Type1_TypeOps        = new String[] {"#Type1", "#TypeOps"} ;
    static String[] arrow_Type           = new String[] {"->", "#Type"} ;
    static String[] Integer              = new String[] {"Integer"} ;
    static String[] Bool                 = new String[] {"Bool"} ;
    static String[] lbr_Type_rbr         = new String[] {"(", "#Type", ")"} ;
    static String[] VAR_Args_eq_Exp      = new String[] {"VAR", "#Args", "=", "#Exp", ";"} ;
    static String[] VAR_Args             = new String[] {"VAR", "#Args"} ;
    static String[] Exp1                 = new String[] {"#Exp1"} ;
    static String[] if_then_else         = new String[] {"if", "#Exp", "then", "#Exp", "else", "#Exp"} ;
    static String[] Exp2_Op1             = new String[] {"#Exp2", "#Op1"} ;
    static String[] eqeq_Exp2            = new String[] {"==", "#Exp2"} ;
    static String[] lteq_Exp2            = new String[] {"<=", "#Exp2"} ;
    static String[] Exp3_Ops2            = new String[] {"#Exp3", "#Ops2"} ;
    static String[] plus_Exp3_Ops2       = new String[] {"+", "#Exp3", "#Ops2"} ;
    static String[] minus_Exp3_Ops2      = new String[] {"-", "#Exp3", "#Ops2"} ;
    static String[] Exp4_Ops3            = new String[] {"#Exp4", "#Ops3"} ;
    static String[] VAR                  = new String[] {"VAR"} ;
    static String[] NUM                  = new String[] {"NUM"} ;
    static String[] BOOLEAN              = new String[] {"BOOLEAN"} ;
    static String[] lbr_Exp_rbr          = new String[] {"(", "#Exp", ")"} ;

    // May add auxiliary methods here if desired

    String[] tableEntry (String nonterm, String tokClass) {
        if (nonterm.equals("#Prog")) {
            if (tokClass == null) return epsilon;
            else if (tokClass.equals("VAR")) return Decl_Prog;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Decl")) {
            if (tokClass.equals("VAR")) return TypeDecl_TermDecl;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#TypeDecl")) {
            if (tokClass.equals("VAR")) return VAR_has_Type;
            else {
                System.out.println("Incorrect Grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Type")) {
            if (tokClass.equals("Integer") || tokClass.equals("Bool") || tokClass.equals("(")) return Type1_TypeOps;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#TypeOps")) {
            if (tokClass.equals(")") || tokClass.equals(";")) return epsilon;
            else if (tokClass.equals("->")) return arrow_Type;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Type1")) {
            if (tokClass.equals("Integer")) return Integer;
            else if (tokClass.equals("Bool")) return Bool;
            else if (tokClass.equals("(")) return lbr_Type_rbr;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#TermDecl")) {
            if (tokClass.equals("VAR")) return VAR_Args_eq_Exp;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Args")) {
            if (tokClass.equals("=")) return epsilon;
            else if (tokClass.equals("VAR")) return VAR_Args;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Exp")) {
            if (tokClass.equals("VAR") || tokClass.equals("NUM") || tokClass.equals("BOOLEAN") || tokClass.equals("(")) return Exp1;
            else if (tokClass.equals("if")) return if_then_else;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Exp1")) {
            if (tokClass.equals("VAR") || tokClass.equals("NUM") || tokClass.equals("BOOLEAN") || tokClass.equals("("))
                return Exp2_Op1;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Op1")) {
            if (tokClass.equals("then") || tokClass.equals("else") || tokClass.equals(")") || tokClass.equals(";"))
                return epsilon;
            else if (tokClass.equals("==")) return eqeq_Exp2;
            else if (tokClass.equals("<=")) return lteq_Exp2;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Exp2")) {
            if (tokClass.equals("VAR") || tokClass.equals("NUM") || tokClass.equals("BOOLEAN") || tokClass.equals("("))
                return Exp3_Ops2;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Ops2")) {
            if (tokClass.equals("then") || tokClass.equals("else") || tokClass.equals(")") || tokClass.equals(";") || tokClass.equals("==") || tokClass.equals("<="))
                    return epsilon;
            else if (tokClass.equals("+")) return plus_Exp3_Ops2;
            else if (tokClass.equals("-")) return minus_Exp3_Ops2;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Exp3")) {
            if (tokClass.equals("VAR") || tokClass.equals("NUM") || tokClass.equals("BOOLEAN") || tokClass.equals("("))
                return Exp4_Ops3;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        if (nonterm.equals("#Ops3")) {
            if (tokClass.equals("then") || tokClass.equals("else") || tokClass.equals(")") ||
                tokClass.equals(";")||tokClass.equals("==")||tokClass.equals("<=") || tokClass.equals("+") ||
                tokClass.equals("-"))
                    return epsilon;
            else if (tokClass.equals("VAR") || tokClass.equals("NUM") || tokClass.equals("BOOLEAN") || tokClass.equals("(")) return Exp4_Ops3;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        
        if (nonterm.equals("#Exp4")) {
            if (tokClass.equals("VAR")) return VAR;
            else if (tokClass.equals("NUM")) return NUM;
            else if (tokClass.equals("BOOLEAN")) return BOOLEAN;
            else if (tokClass.equals("(")) return lbr_Exp_rbr;
            else {
                System.out.println("Incorrect grammar");
                return null;
        }
        }
        else return null;
        }
}


// For testing

class MH_ParserDemo {

    static PARSER MH_Parser = new MH_Parser() ;

    public static void main (String[] args) throws Exception {
	Reader reader = new BufferedReader (new FileReader (args[0])) ;
	LEX_TOKEN_STREAM MH_Lexer = 
	    new CheckedSymbolLexer (new MH_Lexer (reader)) ;
	TREE theTree = MH_Parser.parseTokenStream (MH_Lexer) ;
    }
}

