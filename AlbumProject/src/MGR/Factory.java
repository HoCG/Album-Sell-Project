package MGR;

public interface Factory<T extends Manageable> {
	T create();
}
