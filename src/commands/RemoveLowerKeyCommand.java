package commands;

/**
 * Класс команды remove_lower_key
 */
public class RemoveLowerKeyCommand extends Command {
    public RemoveLowerKeyCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            commandManager.receiver.removeLowerKey(id);
        } catch (Exception e) {
            System.out.println("Неверное значение ключа. Попробуйте ввести целое число");
        }

    }
}
