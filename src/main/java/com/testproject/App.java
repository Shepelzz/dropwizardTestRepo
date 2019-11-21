package com.testproject;

import com.mongodb.*;

import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception{

        User user = createUser();
        DBObject doc = createDBObject(user);

        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("journaldev");

        DBCollection col = db.getCollection("users");

        //create user
        WriteResult result = col.insert(doc);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
        System.out.println(result.getLastConcern());

        //read example
        DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();
        DBCursor cursor = col.find(query);
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }

        //update example
        user.setName("Pankaj Kumar");
        doc = createDBObject(user);
        result = col.update(query, doc);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
        System.out.println(result.getLastConcern());

        //delete example
        result = col.remove(query);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
        System.out.println(result.getLastConcern());

        //close resources
        mongo.close();
    }

    private static DBObject createDBObject(User user) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("_id", user.getId());
        docBuilder.append("name", user.getName());
        docBuilder.append("role", user.getRole());
        docBuilder.append("isEmployee", user.isEmployee());
        return docBuilder.get();
    }

    private static User createUser() {
        User u = new User();
        u.setId(2);
        u.setName("Pankaj");
        u.setEmployee(true);
        u.setRole("CEO");
        return u;
    }
}
