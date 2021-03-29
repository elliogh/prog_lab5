package commands;

/**
 * Класс команды remove_key
 */
public class RemoveKeyCommand extends Command{
    public RemoveKeyCommand(String key, String helpText) {
        super(key, helpText);
    }

    @Override
    public void execute(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            commandManager.receiver.removeKey(id);
        } catch (Exception e) {
            System.out.println("Неверное значение ключа. Попробуйте ввести целое число");
        }

    }
}
