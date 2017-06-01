package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * Current state of a task
 */
public enum TaskState {
    IDLE,
    NOT_CONNECTED,
    STARTED,
    COMPLETED,
    WAITING,
    CANCELLED,
    RESUME,
    ERROR,
    PAUSE;

}
