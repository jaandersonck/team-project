package view;

import interface_adapter.select_repository.SelectRepositoryController;
import interface_adapter.select_repository.SelectRepositoryState;
import interface_adapter.select_repository.SelectRepositoryViewModel;

import interface_adapter.select_repository.SelectRepositoryViewModelListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectRepositoryView extends JPanel implements ActionListener, SelectRepositoryViewModelListener {

    // Add the SearchRepositoryViewModel on this view class also
    //
    // searched and from there we need to add an actionlistener to this searched repository view that selects the repository that was searched
    // we can add the search textbox and the search button here along with actionlistener that calls the SearchRepositoryController

    private final SelectRepositoryViewModel selectRepositoryViewModel;


    private final JPanel repositorylistPanel = new JPanel();

    private final List<JButton> buttons = new ArrayList<>();
    private SelectRepositoryController selectRepositoryController = null;

    public SelectRepositoryView(SelectRepositoryViewModel selectRepositoryViewModel) {

        this.selectRepositoryViewModel = selectRepositoryViewModel;
        this.selectRepositoryViewModel.addListener(this);

        final JLabel title = new JLabel("Select Repository");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        List<String> repositories = selectRepositoryViewModel.getState().getRepositories();




    }

}
