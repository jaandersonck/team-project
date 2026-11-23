package interface_adapter.select_repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The View Model for Select Repository
 */

public class SelectRepositoryViewModel {

    private final List<SelectRepositoryViewModelListener> listeners = new ArrayList<>();

    public static final String SELECT_REPOSITORY = "select repository";

    private SelectRepositoryState state = new SelectRepositoryState();

    public SelectRepositoryState getState(){ return this.state; }

    public void setState(SelectRepositoryState state){ this.state = state; }

    public void addListener(SelectRepositoryViewModelListener listener){ listeners.add(listener); }

    public void notifyStateChanged(){
        for (SelectRepositoryViewModelListener listener : listeners){
            listener.stateChanged();
        }
    }


}
