# Caesar Cipher

When many of us were younger, we enjoyed writing secret messages, in which messages were encoded in
such a way as to prevent others from reading them, unless they were in possession of a secret that
enabled them to decode the message. Coded messages of this sort have a long history. For example,
the **Caesar Cipher** is a simple means of encoding messages that dates from Roman times.

To illustrate, when applied to the historic phrase:

> One if by land, two if by sea.

The **Caesar Cipher** produces the encoded sentence:

> Rqh li eb odqg, wzr li eb vhd.

What is the relationship between the letters in the original sentence and those in the encoded
sentence?

Today's exercise is to use the **Caesar Cipher** to **encode** and **decode** messages stored in
files.

---

## Getting Started

Take a look at the classes `Encode` and `Decode`, as well as the files `message.txt` and `alice.code`. Those are the files we'll be working on.

##
## An Encoding Program 
The first part of today's exercise is to write a program that can be used to
**encode** a message that is stored in a file. To demonstrate both input from and output to a file, we
will store the encoded message in a second file.

## 
##Behavior

1. Our program should display a greeting and then prompt for and **read the name of the input file**.


2. It should then try to **connect an input stream to that file** so that we can read from it, and print a
diagnostic message if the stream does not open correctly. 


3. It should then prompt for and **read the name of the output file**. 


4. It should then try to **connect an output stream to that file** so that we can
write to it, and print a diagnostic message if the stream does not open correctly. 

For each character in the input file, our program should:
1. Read the character, 
2. Encode it using the Caesar Cipher, 
3. Output the encoded character to the output file. 

Our program should conclude by **disconnecting the streams from the files**, and then print a **"success"** message. 

##
## Objects

Using this behavioral description, we can identify the following objects:

| Description                                                                   | Type             | Kind     | Name          |
|-------------------------------------------------------------------------------|------------------|----------|---------------|
| An input stream, to read from the keyboard                                    | `BufferedReader` | varying  | `theKeyboard` |
| An output stream, to print on the console                                     | `PrintStream`    | varying  | `System.out`  |
| a greeting                                                                    | `String`         | constant | none          |
| The name of the input file                                                    | `String`         | varying  | `inFileName`  |
| An input stream, which is the file that has to be encoded                     | `BufferedReader` | varying  | `inStream`    |
| The name of the output file                                                   | `String`         | varying  | `outFileName` |
| An output stream, which is the file that has been generated from the encoding | `BufferedWriter` | varying  | `outStream`   |
| A character from the input file                                               | `int`            | varying  | `inValue`     |
| An encoded character                                                          | `char`           | varying  | `outChar`     |

Using this list of objects, we might specify the behavior of our program as follows:

- Input(`inFileName`): a sequence of unencoded characters;
- Output(`outFileName`): a sequence of encoded characters. 

## Operations

From our behavioral description, we have these operations:

|    | Description                                  | Name                                                     | Package/Class                                    |
|----|----------------------------------------------|----------------------------------------------------------|--------------------------------------------------|
| 1  | Display a String                             | `println()`                                              | `java.io.PrintWriter`                              |
| 2  | Read a String                                | `readLine()`                                             | `java.io.BufferedReader`                           |
| 3  | Connect an input stream to a file            | `BufferedReader` constructor                             | `java.io.BufferedReader`                           |
|    |                                              | `FileReader` constructor                                 | `java.io.FileReader`                               |
| 4  | Connect an output stream to a file           | `PrintWriter` constructor                                | `java.io.PrintWriter`                              |
|    |                                              | `FileWriter` Constructor                                 | `java.io.FileWriter`                               |
| 5  | Check that a stream opened properly          | An `IOException` is thrown if it doesn't                 |                                                  |
| 6  | Read a char from an input stream             | `read()`                                                 | `java.io.BufferedReader`                           |
| 7  | Encode a char using the Caesar Cipher        | `caesarEncode()`                                         | `Encode`                                           |
| 8  | Write a char to an output stream             | `write()`                                                | `java.io.PrintWriter`                              |
| 9  | Repeat 6, 7, 8 for each char in the file     | input loop                                               | built-in                                         |
| 10 | Determine when all characters have been read | `read()` returns `-1` when no chars remains to be read   |                                                  |
| 11 | Disconnect the stream from the file          | `close()`                                                | `java.io.BufferedReader`, `java.io.PrintWriter`  |

##
## Algorithm

We can organize these operations into the following algorithm:

1. Create an input stream to the keyboard using `System.in`.
2. Display a greeting message.
3. Prompt for and read `inFileName`, the name of the input file.
4. Create a `BufferedReader` named `inStream` connecting our program to `inFileName`.
5. Check that `inStream` opened correctly.
6. Prompt for and read `outFileName`, the name of the output file.
7. Create a `BufferedWriter` named `outStream` connecting our program to `outFileName`.
8. Check that `outStream` opened correctly.
9. Loop through the following steps:
   1. read a character from the input file. 
   2. if end-of-file was reached, then terminate repetition. 
   3. encode the character. 
   4. write the encoded character to the output file. End loop.
10. Close the input and output connections.
11. Display a "successful completion" message.

#
#
#Coding 

`Encode` already implements a number of these steps. It should be evident that we can perform:

- step 2 with a relatively familiar output statement, 
- step 9 using a forever loop, containing an if-break combination in step 9.ii to control the repetition, and 
- step 9.iii using the `caesarEncode()` method that follows the main function. 

That leaves the file-related operations in steps 1, 3, 4, 5,
6, 7, 8, 9.i, 9.iv and 10 for us to learn how to perform.

##
## Wrapping `System.in` with a `BufferedReader`

As mentioned previously, `System.in` only provides a relatively low level access. We would like to
create a `BufferedReader` based on `System.in`. The only problem with this is that both of the
constructors for `BufferedReader` take an argument that is a `Reader`. So first we need to create a
`Reader` using `System.in`.

If we look at the documentation for `Reader` we see that it has a subclass (also acceptable) which is
an `InputStreamReader`. `InputStreamReader` is a bridge class between streams and readers and has a
constructor that will take an `InputStream`.

###
We do:

```java
new InputStreamReader(System.in);
```

to create a `Reader` out of a `InputStream`. We take that and use it to create the `BufferedReader`:

```java
new BufferedReader(new InputStreamReader(System.in));
```

Using this information, implement step 1 of our algorithm in the `Encode` class.

##
## Reading a line from a `BufferedReader` 

One might think that:

```java
inFileName = theKeyboard.readLine();
```

would be sufficient to read in the name of the file. Unfortunately, if we try to compile this code,
we will get an error message. This code has the possibility of throwing an `IOException`. Java forces us to
deal with this type of exception. So we need to wrap this code in a `try/catch` block:

```java
try {
  inFileName = theKeyboard.readLine();
} catch(IOException ex) {
  System.err.println("Failed to read file name: " + ex.getMessage());
  System.exit(1);
}
```

Using this information, implement **step 3** of our algorithm in the `Encode` class.

##
## Opening a Connection to a `File`

An executing program is unable to interact directly with a file for a very simple reason: an
executing program resides in main memory and a file resides on a secondary memory device, such as a
hard disk. 

However, an executing program can interact indirectly with a file, by opening a
connection between the program and that file. In Java, such connections are `FileReader` or `FileWriter`
objects as mentioned before.

Like any other object, a `FileReader` must be created before it can be used. 

Since our files are located in the **resources folder** and not in the same folder as the Java files,
we need to get access to the resource folder. We achieve it programatically with:

```java
URL inResource = Decode.class.getClassLoader().getResource(inFileName);
Objects.requireNonNull(inResource);
```

With that, we can get the `URL` of the resource provided, given that the resource is the one provided
by the current `ClassLoader`. The second line verifies that the resource exists.

Using the `URL inResource`, create an instance of `FileReader`. You can get the URL's value as a String
by calling the method `inResource.getPath()` to the `FileReader`. Again, also wrap it with a 
`BufferedReader` for efficiency.

Using this information, implement step 4 of our algorithm in
the `Encode` class by declaring a `BufferedReader` named `inStream` that serves as a connection between our
program and the file whose name is in `inFileName`.

#
To perform step 6 of our algorithm, we must open a `FileWriter` for output to the output file. Use the 
`URL outResource` to achieve it:

```java
URL outResource = Decode.class.getClassLoader().getResource("");
Objects.requireNonNull(outResource);
```

Like before, we get the resource, but this time we provide `""` as an argument, so we get the root 
resource folder.

Since we want to create a new file in the root resource folder, we use:

```java
new FileWriter(resource.getPath() + outFile);
```

Similarly, we will want to use this object to create a `BufferedWriter` for efficiency.

Using this information, implement **step 6** of our algorithm by declaring a `BufferedWriter` named
`outStream` that serves as a connection between our program and the file whose name is in `outFileName`.

##
##Checking that a Connection Opened Correctly

Opening files is an operation that is highly susceptible to user errors. For example, suppose the
user has accidentally deleted the input file and our program tries to open a connection to it? In
Java, if there is a problem opening a file to be read, a `FileNotFoundException` will be thrown. Again
this exception requires our attention in the form of a `try/catch`.

If there is a problem opening a file to be written, an `IOException` will be thrown,

Complete steps 5 and 8 by wrapping the creation of `inFileName` an `outFileName` with `try/catches`. If
there is an exception, print an error message and exit the program.

> When your source program compiles correctly (except possibly for an error indicating that the
completion message statement may not be reached) continue on to the next part of the exercise. Do
not execute your source program yet or an infinite loop will occur.

##
## Input from a `BufferedReader`

We are interested in reading one character at a time. The `read()` method of the `BufferedReader` class
almost does what we want. It will read a character from the file, but it returns an `int` value (32
bit). This operation is described as reading from the file, even though we are actually operating on
the `BufferedReader`.

```java
intVariable = inStream.read();
```

 Once again, if there is an error, an `IOException` will be thrown, and
we are forced to implement a `try/catch` to handle this.

Using this information, implement step 9.i of our algorithm. Then compile your program, and continue
when what you have written is syntactically correct.

##
## Controlling a File-Input Loop

Files are created by a computer's operating system. When the operating system creates a file, it
marks the end of the file with a special end-of-file mark. Input operations are then implemented in
such a way as to prevent them from reading beyond the end-of-file mark, since doing so could allow a
programmer unauthorized access to the files of another programmer.

This end-of-file mark can be used to control a loop that is reading data from the file. Java
indicates an end of file by having `read()` return the value -1. If you do a read after that, an
exception will be generated.

The expression

```java
inValue == -1
```

will allow us to determine if the end of the file has been reached.

In a forever loop like the one in the source program, we can prevent infinite loop behavior by
placing an `if-break` combination:

```java
if ( /* end-of-file has been reached */ ) break;
```

following the input step, repetition will be
terminated when all the data in the file has been processed.

In your source program, place an if-break combination in the appropriate place to perform **step 9.ii** of
our algorithm. 

> Compile your source program to check the syntax of what you have written. At
this point, you should not have any syntactical errors left in your program. When it is correct,
continue to the next part of the exercise.

##
## File Output

We need to be able to write a character on the output file. If we examine the operations in
`BufferedWriter`, we see that there is a `write()` method that will write a single character. This is
just what we need.

The general form to use `write()` is:

```java
outputStreamName.write(charValue);
```

where `outputStreamName` is a `FileWriter` or `BufferedWriter`, and
`charVariable` is a character we wish to store in the file to which `outputStreamName` is a connection to.
Once again, if there is a problem, an `IOException` will be thrown which we must catch.

Use this information to write the encoded character to your output file via `outStream`, to perform
**step 9.iv** of our algorithm. Then compile your source program to test the syntax of what you have
written, continuing when it is correct.

##
## Closing Files

Once we are done using a stream to read from or write to a file, we should close it, to break the
connection between our program and the file. This is accomplished using the method `close()`, whose
statement form is

```java
streamName.close();
```

 When execution reaches this statement, the program ends its connection to
`streamName`. Once again, if there is a problem, an `IOException` will be thrown that must be dealt
with.

In the appropriate place in the source program, place calls to `close()` to:

- break the connection between the program and the input file; and 
- break the connection between the program and the output file. 

Then compile your source program, and ensure that it is free of syntax errors.

##
##Testing and Debugging
When your program's syntax is correct, test it using the provided file named `message.txt`. 
If what you have written is correct, your program should create an output file (e.g.,
`message.code`), containing the output:

> Rqh Li Eb Odqg\
> Wzr Li Eb Vhd

Keep in mind that, since we're using resources, the output file will be generated in the out folder.
If this file is not produced, then your program contains a logical error. Retrace your steps, 
comparing the statements in your source program to those described in the
preceding parts of the exercise, until you find your error. Correct it, retranslate your source
program and then retest your program, until it performs correctly.

##
## The `Decode` Class

The last part of the exercise is for you to apply what you have learned to the problem of _decoding_ a 
file encoded using the Caesar Cipher. Complete the skeleton program in the `Decode` class, that can 
be used to decode a message encoded using the Caesar cipher. Do all
that is necessary to get this program operational, so that messages encoded with `Encode` class can be
decoded with `Decode` class. 

Put differently, the two programs should complement one another.

To test your program, you can either use the output file created by the `Encode` class, or the file 
`alice.code`, a selection from Lewis Carroll's Alice In WonderLand.
