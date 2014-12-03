Rapid Android Development Template
==================================

## Tools
Android Studio 0.8.x and Gradle 2.x

## IDE Tools
* Dagger Plugin
* Android ButterKnife Zelezny
* Libraries
* Active Android 3.1.10
* Retrofit 1.7.0
* OkHttp 2.0.0
* Otto Eventbus 1.3.5
* Dagger 1.2.2
* ButterKnife 5.1.2
* Otto Picasso 2.3.2

## Future Integration
* UI validation library Saripaar

## 3rd Parties Social
* Facebook SDK as a git module

## Example how to use SQLite Databse
For quick and easy access to the database, it have been used the ActiveRecord pattern via the ActiveAndroid Library. Into the source code there is a class DBModel<T> which can be used from any OBject/class which want to have access to the SQLite Database. The DBModel<T> extends Model which includes all the necessary methods for accessing the database as load, save, delete, getId.

The DBModel<T> class includes some extra methods according to the type <T> and some helpers which can be used and they are the followings:

* ```java DBModel findOne(Long id) ``` -> Returns the record according to the requested id
* List<? extends DBModel> findAll() -> Returns all the records of the current DBModel<T>
* void deleteOne(Long id) -> Deletes the record according to the requested id
* void deleteAll() -> Deletes all the records of the current DBModel<T>
* Select selectQuery() -> Returns an instance of Select ActiveAndroid (reduce the new Object)
* Delete deleteQuery() -> Returns an instance of Delete ActiveAndroid(reduce the new Object)
* eg. Let's assume that we have a User object which must be saved/selected/updated/delete into the SQLite Database.
```java
@Table(name = 'user')
public class User extends DBModel<User> {

    @Column(name = "email", index = true, unique = true)
    public String email;

    @Column(name = "fname")
    public String fname;

    @Column(name = "lname")
    public String lname;

    @Column(name = "avatar")
    public String avatar;

    public User() {
    }
}
```
We have our Database Model and now we want to access to the database. In this point I want to note that in the DBModel has implemented the Singletton Holder pattern in order to avoid multiple instances of the same DBModel<T>.

Get all available users:
List<User> users = (List<User>) DBModel.getInstance(User.class).findAll();
Get the available user by id:
User searchUser = (User) DBModel.getInstance(User.class).findOne(21L);
Inserts a user into table:
User user = new User();
user.avatar = "foo_bar.jpg";
user.email  = "foo_bar@test.com";
user.lname  = "Bar";
user.fname  = "Foo";
user.save();
Updates a user:
User searchUser = (User) DBModel.getInstance(User.class).findOne(21L);
searchUser.fname  = "FooUpdate";
searchUser.save();
Delete all users:
DBModel.getInstance(User.class).deleteAll();
Delete a user by id:
DBModel.getInstance(User.class).deleteOne(21L);
To be contiuned with details for API requests. Stay tuned :)
