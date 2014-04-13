package wyvern.tools.typedAST.core.declarations;

import wyvern.tools.errors.ErrorMessage;
import wyvern.tools.errors.FileLocation;
import wyvern.tools.errors.ToolError;
import wyvern.tools.imports.ImportBinder;
import wyvern.tools.imports.ImportResolver;
import wyvern.tools.typedAST.abs.Declaration;
import wyvern.tools.typedAST.core.binding.ImportResolverBinding;
import wyvern.tools.typedAST.core.binding.NameBindingImpl;
import wyvern.tools.typedAST.core.binding.TypeBinding;
import wyvern.tools.typedAST.core.binding.ValueBinding;
import wyvern.tools.typedAST.core.values.Obj;
import wyvern.tools.typedAST.interfaces.CoreAST;
import wyvern.tools.typedAST.interfaces.CoreASTVisitor;
import wyvern.tools.typedAST.interfaces.EnvironmentExtender;
import wyvern.tools.typedAST.interfaces.TypedAST;
import wyvern.tools.types.Environment;
import wyvern.tools.types.Type;
import wyvern.tools.types.extensions.TypeType;
import wyvern.tools.types.extensions.Unit;
import wyvern.tools.util.Reference;
import wyvern.tools.util.TreeWriter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ImportDeclaration extends Declaration implements CoreAST {
	private URI uri;
	private ImportBinder binder;
	private FileLocation location;

	public ImportDeclaration(URI inputURI, FileLocation location) {
		this.uri = inputURI;
		this.location = location;
	}


	@Override
	public Environment extendType(Environment env) {
		binder = env.lookupBinding(uri.getScheme(), ImportResolverBinding.class).getBound().resolveImport(uri);
		return binder.extendTypes(env);
	}

	@Override
	public Environment extendName(Environment env, Environment against) {
		return binder.extendNames(env);
	}

	@Override
	protected Type doTypecheck(Environment env) {
		return binder.typecheck(env);
	}

	@Override
	public Type getType() {
		return Unit.getInstance();
	}

	@Override
	public FileLocation getLocation() {
		return location;
	}

	@Override
	public void writeArgsToTree(TreeWriter writer) {

	}

	@Override
	public void accept(CoreASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Map<String, TypedAST> getChildren() {
		Map<String, TypedAST> childMap = new HashMap<>();
		return childMap;
	}

	@Override
	public TypedAST cloneWithChildren(Map<String, TypedAST> newChildren) {
		return this;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	protected Environment doExtend(Environment old) {
		return binder.extend(old);
	}

	@Override
	public Environment extendWithValue(Environment old) {
		return old;
	}

	@Override
	public void evalDecl(Environment evalEnv, Environment declEnv) {

	}
}
