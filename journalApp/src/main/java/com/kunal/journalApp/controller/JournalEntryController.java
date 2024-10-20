package com.kunal.journalApp.controller;

import com.kunal.journalApp.entity.JournalEntry;
import com.kunal.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getALl() {
        List<JournalEntry> all = journalEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> byId = journalEntryService.findById(myId);
        if(byId.isPresent()){
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry old= journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().isEmpty() ?newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ?newEntry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
