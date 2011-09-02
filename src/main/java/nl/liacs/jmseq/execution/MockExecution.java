/*
 * Created on Apr 22, 2010 - 1:10:46 PM
 */
package nl.liacs.jmseq.execution;

import java.util.Arrays;
import java.util.List;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Type;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.request.EventRequest;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class MockExecution extends MethodEntryExecution {

	public MockExecution(String className, String methodName, String[] argTypes, String returnType) {
		super(new MockMethodEntryEvent(methodName, returnType, argTypes), className, null, -1L);

	}

	@Override
	public Class getExecutingEventType() {
		return MockMethodEntryEvent.class;
	}

	static class MockMethodEntryEvent implements MethodEntryEvent {

		private Method method;

		public MockMethodEntryEvent(String methodName, String returnType, String[] argTypes) {
			this.method = new MockMethod(methodName, returnType, argTypes);
		}

		@Override
		public Method method() {
			return method;
		}

		@Override
		public ThreadReference thread() {
			throw new UnsupportedOperationException("This is a mock event");
		}

		@Override
		public EventRequest request() {
			throw new UnsupportedOperationException("This is a mock event");
		}

		@Override
		public VirtualMachine virtualMachine() {
			throw new UnsupportedOperationException("This is a mock event");
		}

		@Override
		public Location location() {
			throw new UnsupportedOperationException("This is a mock event");
		}

	}

	static class MockMethod implements Method {

		private String methodName;
		private List<String> argTypes;
		private String returnType;

		public MockMethod(String methodName, String returnType, String[] argTypes) {
			this.methodName = methodName;
			this.argTypes = Arrays.asList(argTypes);
			this.returnType = returnType;
		}

		@Override
		public List<Location> allLineLocations() throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public List<Location> allLineLocations(String arg0, String arg1) throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public List<String> argumentTypeNames() {
			return this.argTypes;
		}

		@Override
		public List<Type> argumentTypes() throws ClassNotLoadedException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public List<LocalVariable> arguments() throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public byte[] bytecodes() {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public boolean isAbstract() {
			return false;
		}

		@Override
		public boolean isBridge() {
			return false;
		}

		@Override
		public boolean isConstructor() {
			return false;
		}

		@Override
		public boolean isNative() {
			return false;
		}

		@Override
		public boolean isObsolete() {
			return false;
		}

		@Override
		public boolean isStaticInitializer() {
			return false;
		}

		@Override
		public boolean isSynchronized() {
			return false;
		}

		@Override
		public boolean isVarArgs() {
			return false;
		}

		@Override
		public Location location() {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public Location locationOfCodeIndex(long arg0) {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public List<Location> locationsOfLine(int arg0) throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public List<Location> locationsOfLine(String arg0, String arg1, int arg2) throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public Type returnType() throws ClassNotLoadedException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public String returnTypeName() {
			return this.returnType;
		}

		@Override
		public List<LocalVariable> variables() throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public List<LocalVariable> variablesByName(String arg0) throws AbsentInformationException {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public ReferenceType declaringType() {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public String genericSignature() {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public boolean isFinal() {
			return false;
		}

		@Override
		public boolean isStatic() {
			return false;
		}

		@Override
		public boolean isSynthetic() {
			return false;
		}

		@Override
		public String name() {
			return this.methodName;
		}

		@Override
		public String signature() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VirtualMachine virtualMachine() {
			throw new UnsupportedOperationException("This is a mock method");
		}

		@Override
		public boolean isPackagePrivate() {
			return false;
		}

		@Override
		public boolean isPrivate() {
			return false;
		}

		@Override
		public boolean isProtected() {
			return false;
		}

		@Override
		public boolean isPublic() {
			return true;
		}

		@Override
		public int modifiers() {
			return 0;
		}

		@Override
		public int compareTo(Method o) {
			return signature().compareTo(o.signature());
		}

	}

}
