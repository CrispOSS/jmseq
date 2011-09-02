package mop.ERE.HasNext;
import java.io.*;
import java.util.*;
import javamoprt.*;

class HasNextMonitor_1_Set implements javamoprt.MOPSet {
	protected HasNextMonitor_1[] elementData;
	public int size;

	public HasNextMonitor_1_Set(){
		this.size = 0;
		this.elementData = new HasNextMonitor_1[4];
	}

	public final int size(){
		while(size > 0 && elementData[size-1].MOP_terminated) {
			elementData[--size] = null;
		}
		return size;
	}

	public final boolean add(MOPMonitor e){
		ensureCapacity();
		elementData[size++] = (HasNextMonitor_1)e;
		return true;
	}

	public final void endObject(int idnum){
		for(int i = 0; i < size; i++){
			MOPMonitor monitor = elementData[i];
			if(!monitor.MOP_terminated){
				monitor.endObject(idnum);
			}
		}
	}

	public final boolean alive(){
		for(int i = 0; i < size; i++){
			MOPMonitor monitor = elementData[i];
			if(!monitor.MOP_terminated){
				return true;
			}
		}
		return false;
	}

	public final void endObjectAndClean(int idnum){
		for(int i = 0; i < size; i++){
			MOPMonitor monitor = elementData[i];
			if(!monitor.MOP_terminated){
				monitor.endObject(idnum);
			}
			elementData[i] = null;
		}
		elementData = null;
	}

	public final void ensureCapacity() {
		int oldCapacity = elementData.length;
		if (size + 1 > oldCapacity) {
			cleanup();
		}
		if (size + 1 > oldCapacity) {
			Object oldData[] = elementData;
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < size + 1){
				newCapacity = size + 1;
			}
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
	}

	public final void cleanup() {
		int num_terminated_monitors = 0 ;
		for(int i = 0; i + num_terminated_monitors < size; i ++){
			HasNextMonitor_1 monitor = (HasNextMonitor_1)elementData[i + num_terminated_monitors];
			if(monitor.MOP_terminated){
				if(i + num_terminated_monitors + 1 < size){
					do{
						monitor = (HasNextMonitor_1)elementData[i + (++num_terminated_monitors)];
					} while(monitor.MOP_terminated && i + num_terminated_monitors + 1 < size);
					if(monitor.MOP_terminated){
						num_terminated_monitors++;
						break;
					}
				} else {
					num_terminated_monitors++;
					break;
				}
			}
			if(num_terminated_monitors != 0){
				elementData[i] = monitor;
			}
		}
		if(num_terminated_monitors != 0){
			size -= num_terminated_monitors;
			for(int i = size; i < size + num_terminated_monitors ; i++){
				elementData[i] = null;
			}
		}
	}

	public final void event_hasnext(Iterator i) {
		int num_terminated_monitors = 0 ;
		for(int i_1 = 0; i_1 + num_terminated_monitors < this.size; i_1 ++){
			HasNextMonitor_1 monitor = (HasNextMonitor_1)this.elementData[i_1 + num_terminated_monitors];
			if(monitor.MOP_terminated){
				if(i_1 + num_terminated_monitors + 1 < this.size){
					do{
						monitor = (HasNextMonitor_1)this.elementData[i_1 + (++num_terminated_monitors)];
					} while(monitor.MOP_terminated && i_1 + num_terminated_monitors + 1 < this.size);
					if(monitor.MOP_terminated){
						num_terminated_monitors++;
						break;
					}
				} else {
					num_terminated_monitors++;
					break;
				}
			}
			if(num_terminated_monitors != 0){
				this.elementData[i_1] = monitor;
			}
			monitor.event_hasnext(i);
			if(monitor.Category_fail) {
				System.err.println("! hasNext() has not been called" + " before calling next() for an" + " iterator");
				monitor.reset();
			}
		}
		if(num_terminated_monitors != 0){
			this.size -= num_terminated_monitors;
			for(int i_1 = this.size; i_1 < this.size + num_terminated_monitors; i_1++){
				this.elementData[i_1] = null;
			}
		}
	}

	public final void event_next(Iterator i) {
		int num_terminated_monitors = 0 ;
		for(int i_1 = 0; i_1 + num_terminated_monitors < this.size; i_1 ++){
			HasNextMonitor_1 monitor = (HasNextMonitor_1)this.elementData[i_1 + num_terminated_monitors];
			if(monitor.MOP_terminated){
				if(i_1 + num_terminated_monitors + 1 < this.size){
					do{
						monitor = (HasNextMonitor_1)this.elementData[i_1 + (++num_terminated_monitors)];
					} while(monitor.MOP_terminated && i_1 + num_terminated_monitors + 1 < this.size);
					if(monitor.MOP_terminated){
						num_terminated_monitors++;
						break;
					}
				} else {
					num_terminated_monitors++;
					break;
				}
			}
			if(num_terminated_monitors != 0){
				this.elementData[i_1] = monitor;
			}
			monitor.event_next(i);
			if(monitor.Category_fail) {
				System.err.println("! hasNext() has not been called" + " before calling next() for an" + " iterator");
				monitor.reset();
			}
		}
		if(num_terminated_monitors != 0){
			this.size -= num_terminated_monitors;
			for(int i_1 = this.size; i_1 < this.size + num_terminated_monitors; i_1++){
				this.elementData[i_1] = null;
			}
		}
	}
}

class HasNextMonitor_1 extends javamoprt.MOPMonitor implements Cloneable {
	public Object clone() {
		try {
			HasNextMonitor_1 ret = (HasNextMonitor_1) super.clone();
			return ret;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	int state;
	static final int transition_hasnext[] = {1, 1, 2};;
	static final int transition_next[] = {2, 0, 2};;

	boolean Category_fail = false;

	public HasNextMonitor_1 () {
		state = 0;
	}

	public final void event_hasnext(Iterator i) {
		MOP_lastevent = 0;

		state = transition_hasnext[state];
		Category_fail = state == 2;
	}

	public final void event_next(Iterator i) {
		MOP_lastevent = 1;

		state = transition_next[state];
		Category_fail = state == 2;
	}

	public final void reset() {
		state = 0;
		MOP_lastevent = -1;
		Category_fail = false;
	}

	public javamoprt.MOPWeakReference MOPRef_i;

	//alive_parameters_0 = [Iterator i]
	public boolean alive_parameters_0 = true;

	public final void endObject(int idnum){
		switch(idnum){
			case 0:
			alive_parameters_0 = false;
			break;
		}
		switch(MOP_lastevent) {
			case -1:
			return;
			case 0:
			//hasnext
			//alive_i
			if(!(alive_parameters_0)){
				MOP_terminated = true;
				return;
			}
			break;

			case 1:
			//next
			//alive_i
			if(!(alive_parameters_0)){
				MOP_terminated = true;
				return;
			}
			break;

		}
		return;
	}

}

public aspect HasNextMonitorAspect {
	javamoprt.MOPMapManager HasNextMapManager;
	public HasNextMonitorAspect(){
		HasNextMapManager = new javamoprt.MOPMapManager();
		HasNextMapManager.start();
	}
	static Object HasNext_MOPLock = new Object();

	static javamoprt.MOPMap HasNext_i_Map = new javamoprt.MOPMapOfMonitor(0);
	static Object HasNext_i_Map_cachekey_0 = null;
	static Object HasNext_i_Map_cachevalue = null;

	pointcut HasNext_hasnext(Iterator i) : (call(* Iterator.hasNext()) && target(i)) && !within(HasNextMonitorAspect) && !within(HasNextMonitor_1_Set) && !within(HasNextMonitor_1) && !adviceexecution();
	after (Iterator i) : HasNext_hasnext(i) {
		Object obj = null;
		javamoprt.MOPMap m;
		HasNextMonitor_1 monitor = null;
		HasNextMonitor_1_Set monitors = null;
		javamoprt.MOPWeakReference TempRef_i;

		synchronized(HasNext_MOPLock) {
			if(i == HasNext_i_Map_cachekey_0){
				obj = HasNext_i_Map_cachevalue;
			}

			if(obj == null) {
				obj = HasNext_i_Map.get(i);

				monitor = (HasNextMonitor_1) obj;
				if (monitor == null){
					monitor = new HasNextMonitor_1();
					monitor.MOPRef_i = new javamoprt.MOPWeakReference(i);
					HasNext_i_Map.put(monitor.MOPRef_i, monitor);
				}
				HasNext_i_Map_cachekey_0 = i;
				HasNext_i_Map_cachevalue = monitor;
			} else {
				monitor = (HasNextMonitor_1) obj;
			}
			monitor.event_hasnext(i);
			if(monitor.Category_fail) {
				System.err.println("! hasNext() has not been called" + " before calling next() for an" + " iterator");
				monitor.reset();
			}
		}
	}

	pointcut HasNext_next(Iterator i) : (call(* Iterator.next()) && target(i)) && !within(HasNextMonitorAspect) && !within(HasNextMonitor_1_Set) && !within(HasNextMonitor_1) && !adviceexecution();
	before (Iterator i) : HasNext_next(i) {
		Object obj = null;
		javamoprt.MOPMap m;
		HasNextMonitor_1 monitor = null;
		HasNextMonitor_1_Set monitors = null;
		javamoprt.MOPWeakReference TempRef_i;

		synchronized(HasNext_MOPLock) {
			if(i == HasNext_i_Map_cachekey_0){
				obj = HasNext_i_Map_cachevalue;
			}

			if(obj == null) {
				obj = HasNext_i_Map.get(i);

				monitor = (HasNextMonitor_1) obj;
				if (monitor == null){
					monitor = new HasNextMonitor_1();
					monitor.MOPRef_i = new javamoprt.MOPWeakReference(i);
					HasNext_i_Map.put(monitor.MOPRef_i, monitor);
				}
				HasNext_i_Map_cachekey_0 = i;
				HasNext_i_Map_cachevalue = monitor;
			} else {
				monitor = (HasNextMonitor_1) obj;
			}
			monitor.event_next(i);
			if(monitor.Category_fail) {
				System.err.println("! hasNext() has not been called" + " before calling next() for an" + " iterator");
				monitor.reset();
			}
		}
	}
}
