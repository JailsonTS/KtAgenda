package ui;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JButton buttonNewContato;
    private JButton buttonRemove;
    private JTable tableContacts;
    private JLabel labelContactsCount;

    private ContactBusiness mContactBusiness;
    private String mName = "";
    private String mPhone = "";

    public MainForm(){
        setContentPane(rootPanel);
        setSize(500,250);
        setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/ 2 - getSize().width/2, dim.height/ 2 - getSize().height/2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mContactBusiness = new ContactBusiness();
        setListeners();
        loadContacts();
    }

    private void loadContacts() {
        List<ContactEntity> contactList= mContactBusiness.getList();

        String[] columnNames = {"Nome", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for (ContactEntity entity : contactList) {
            Object[] o = new Object[2];
            o[0] = entity.getName();
            o[1] = entity.getPhone();

            model.addRow(o);
        }

        this.tableContacts.clearSelection();
        this.tableContacts.setModel(model);

        // Atualiza total de contatos
        labelContactsCount.setText(mContactBusiness.getContactCountDescription());
    }

    private void setListeners(){
        buttonNewContato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContactForm();
                dispose();
            }
        });

        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    mContactBusiness.delete(mName, mPhone);
                    mName = "";
                    mPhone = "";

                } catch (Exception excp) {
                    JOptionPane.showMessageDialog(new JFrame(), excp.getMessage(), "Erro ao remover", JOptionPane.ERROR_MESSAGE);
                } finally {
                    loadContacts();
                }
            }
        });

        this.tableContacts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {

                    // Obtém o valor da linha que foi clicada
                    if (tableContacts.getSelectedRow() != -1) {
                        mName = tableContacts.getValueAt(tableContacts.getSelectedRow(), 0).toString();
                        mPhone = tableContacts.getValueAt(tableContacts.getSelectedRow(), 1).toString();
                    }

                }
            }
        });
    }
}
