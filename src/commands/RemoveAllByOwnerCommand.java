package commands;

/**
 * Класс команды remove_all_by_owner
 */
public class RemoveAllByOwnerCommand extends Command{
    public RemoveAllByOwnerCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.removeAllByOwner();
    }
}
