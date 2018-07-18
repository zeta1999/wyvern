/* Generated By:JJTree: Do not edit this line. ASTConnectorTypeDecl.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,
NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package wyvern.tools.parsing.coreparser.arch;

import java.util.HashMap;

import wyvern.target.corewyvernIL.VarBinding;
import wyvern.target.corewyvernIL.decl.Declaration;
import wyvern.target.corewyvernIL.decl.TypeDeclaration;
import wyvern.target.corewyvernIL.decltype.DeclType;
import wyvern.target.corewyvernIL.decltype.ValDeclType;
import wyvern.target.corewyvernIL.expression.IExpr;
import wyvern.target.corewyvernIL.expression.New;
import wyvern.target.corewyvernIL.expression.SeqExpr;
import wyvern.target.corewyvernIL.modules.Module;
import wyvern.target.corewyvernIL.support.InterpreterState;
import wyvern.target.corewyvernIL.type.NominalType;
import wyvern.target.corewyvernIL.type.StructuralType;
import wyvern.tools.errors.ErrorMessage;
import wyvern.tools.errors.HasLocation;
import wyvern.tools.errors.ToolError;

public class ASTConnectorTypeDecl extends SimpleNode {
    private String typeName;
    private HashMap<String, String> vals = new HashMap<>();

    public ASTConnectorTypeDecl(int id) {
        super(id);
    }

    public ASTConnectorTypeDecl(ArchParser p, int id) {
        super(p, id);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String t) {
        typeName = t;
    }

    public String toString() {
        return super.toString() + " " + typeName;
    }

    public void collectVals() {
        for (Node child : children) {
            if (child instanceof ASTValDecl) {
                String name = ((ASTValDecl) child).getName();
                String type = ((ASTValDecl) child).getType();
                vals.put(name, type);
            }
        }
    }

    public boolean checkModule(InterpreterState state) {
        Module mod = null;
        try {
            mod = state.getResolver().resolveType(typeName + "Properties");
            for (HasLocation i : ((SeqExpr) mod.getExpression()).getElements()) {
                IExpr expr = ((VarBinding) i).getExpression();
                if (expr instanceof New) {
                    New n = (New) expr;
                    for (Declaration d : n.getDecls()) {
                        if (d instanceof TypeDeclaration // contains metadata
                                && d.getName().equals(typeName + "Properties")) {
                            StructuralType t = (StructuralType) ((TypeDeclaration) d)
                                    .getSourceType();
                            for (DeclType dt : t.getDeclTypes()) {
                                ValDeclType vdt = (ValDeclType) dt;
                                String name = vdt.getName();
                                String type = ((NominalType) vdt.getSourceType())
                                        .getTypeMember();
                                if (!vals.get(name).equals(type)) {
                                    ToolError.reportError(
                                            ErrorMessage.CONNECTOR_VAL_INCONSISTENCY, location,
                                            typeName);
                                }
                            }
                            return true;
                        }
                    }
                    ToolError.reportError(ErrorMessage.TYPE_NOT_DEFINED, location,
                            typeName);
                }
            }
        } catch (ToolError e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * Accept the visitor.
     **/
    public Object jjtAccept(ArchParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
/*
 * JavaCC - OriginalChecksum=884a938c5fd0ec5939d4c5c7e8dc6912 (do not edit this
 * line)
 */
