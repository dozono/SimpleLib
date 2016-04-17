package api.simplelib.interactive.process;

import api.simplelib.VarSync;
import api.simplelib.Context;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.interactive.process.VarInteger;
import net.simplelib.interactive.process.VarItemHolder;

/**
 * This interface indicates a core updatable logic.
 * <p>To use this with
 * {@link InteractiveMetadata}, I highly recommend use the {@link VarSync} for those
 * variables needed to be synchronized between client and server.
 * </p>
 * <p>There are two special type of variables which need to be noticed,
 * {@link VarItemHolder} and {@link VarInteger}</p>
 * <p>Use {@link VarItemHolder} instead of {@link net.minecraft.item.ItemStack} </p>
 * <p>Use {@link VarInteger} instead of normal integer</p>
 *
 * @author ci010
 */
public interface Process
{
	void preUpdate(Context context);

	boolean shouldUpdate(Context context);

	void update(Context context);

	void postUpdate(Context context);
}