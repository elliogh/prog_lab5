package commands;

/**
 * Класс команды remove_lower
 */
public class RemoveLowerCommand extends Command{
    public RemoveLowerCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        commandManager.receiver.removeLower();
    }
}
