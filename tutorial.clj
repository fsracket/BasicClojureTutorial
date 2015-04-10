;; gorilla-repl.fileformat = 1

;; **
;;; # A quick overview of Clojure basics
;;; 
;;; I'm not going to show how to install Clojure or set up a dev environment.  Lots of tutorials for that and at this point it's really trivial to do it (for the record, I use Intellij, Cursive and lein).  The remainder of the document shows me interacting with the clojure repl.  You could follow along assuming you've started a clojure repl with something like 'lein repl'.
;;; 
;;; ## Primitives
;;; 
;;; Clojure provides primitive data types that are more or less in line with other languages.
;;; 
;;; Numbers...
;; **

;; @@
1
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}
;; <=

;; @@
2.5
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>2.5</span>","value":"2.5"}
;; <=

;; @@
12N ;;big int
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-bigint'>12N</span>","value":"12N"}
;; <=

;; @@
2M ;;decimal -- oh as you've guessed, comments start with ';'
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-bigdecimal'>2M</span>","value":"2M"}
;; <=

;; **
;;; Strings... (Clojure strings are java strings. This has certain implications which will be discussed later).
;; **

;; @@
"hello"
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;hello&quot;</span>","value":"\"hello\""}
;; <=

;; @@
"world"
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;world&quot;</span>","value":"\"world\""}
;; <=

;; **
;;; Characters... (these are Java characters)
;; **

;; @@
\a
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-char'>\\a</span>","value":"\\a"}
;; <=

;; **
;;; And so on.  There are 2 primitive types not typically found in other languages.  Symbols and Keywords.  
;;; 
;;; A symbol is an identifier that may be bound to some value.  They are mostly useful for meta-programming but are sometimes used in other ways (e.g. a quick n dirty way to represent an enum).  Creating a symbol involves a bit of syntactic magic (actually, there really isn't any magic but I don't want to explain why at the moment).  You put a single quote before a series of characters and you have a symbol.
;; **

;; @@
'a
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-symbol'>a</span>","value":"a"}
;; <=

;; @@
'something-really-long
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-symbol'>something-really-long</span>","value":"something-really-long"}
;; <=

;; @@
'a-mix-of-!-n2->?-weird-stuff
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-symbol'>a-mix-of-!-n2-&gt;?-weird-stuff</span>","value":"a-mix-of-!-n2->?-weird-stuff"}
;; <=

;; **
;;; As you can see, Clojure is very flexible about what it allows to be used as a symbol value.  Basically anything that can be used in a variable name in normal Clojure code (and I refer you to the official docs for what is allowed) can be used as a symbol identifier.
;;; 
;;; A keyword is a special kind of symbol that evaluates to itself.  You make a keyword by prefixing some series of characters with ':'
;; **

;; @@
:key1
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:key1</span>","value":":key1"}
;; <=

;; @@
:a-longer-key
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:a-longer-key</span>","value":":a-longer-key"}
;; <=

;; **
;;; ## Typical Data Structures (non-primitives)
;;; 
;;; Unlike older Lisps, Clojure provides literal syntax for a number of common collection data structures. 
;;; 
;;; Square brackets produce a vector.
;; **

;; @@
[1 2 3]
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[1 2 3]"}
;; <=

;; **
;;; Curly brackets with an even number of elements produces a map (dictionary).
;; **

;; @@
{1 "hello"} ;a map with a single entry.  key 1 points to value "hello"
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-string'>&quot;hello&quot;</span>","value":"\"hello\""}],"value":"[1 \"hello\"]"}],"value":"{1 \"hello\"}"}
;; <=

;; @@
{1 "hello" 2 "world"}
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-string'>&quot;hello&quot;</span>","value":"\"hello\""}],"value":"[1 \"hello\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-string'>&quot;world&quot;</span>","value":"\"world\""}],"value":"[2 \"world\"]"}],"value":"{1 \"hello\", 2 \"world\"}"}
;; <=

;; **
;;; Note how the printed representation of the map inserts a comma.  When you write out maps using the literal syntax you can include the comma if you like; it's a matter of personal style.  I'm an old lisp dog so I leave them out.
;; **

;; **
;;; Curly brackets preceded with a # represent a set.  Normal set semantics apply (elements are unique in a set, order doesn't matter).
;; **

;; @@
#{1 2 3}
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-set'>#{</span>","close":"<span class='clj-set'>}</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"#{1 3 2}"}
;; <=

;; @@
#{1 1 2 3 3}
;; @@

;; **
;;; Finally, we come to what is traditionally Lisp's favourite data structure: lists.  Lists are denoted with parentheses.  Because list syntax is used to denote function calls (talked about very soon in the next section), you cannot literally write (1 2 3) and produce a list of numbers.  Instead, prefix the list with a ', like so:  '(1 2 3).
;; **

;; @@
'(1 2 3)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"(1 2 3)"}
;; <=

;; @@
'("mix" and "match" :stuff 12)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;mix&quot;</span>","value":"\"mix\""},{"type":"html","content":"<span class='clj-symbol'>and</span>","value":"and"},{"type":"html","content":"<span class='clj-string'>&quot;match&quot;</span>","value":"\"match\""},{"type":"html","content":"<span class='clj-keyword'>:stuff</span>","value":":stuff"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}],"value":"(\"mix\" and \"match\" :stuff 12)"}
;; <=

;; **
;;; And, of course, data strucutres can be nested arbitrarily as desired.
;; **

;; @@
['(1 2) #{:my 25 "words" {:key1 "hello" :key2 "world"}}]
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"(1 2)"},{"type":"list-like","open":"<span class='clj-set'>#{</span>","close":"<span class='clj-set'>}</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-string'>&quot;words&quot;</span>","value":"\"words\""},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:key2</span>","value":":key2"},{"type":"html","content":"<span class='clj-string'>&quot;world&quot;</span>","value":"\"world\""}],"value":"[:key2 \"world\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:key1</span>","value":":key1"},{"type":"html","content":"<span class='clj-string'>&quot;hello&quot;</span>","value":"\"hello\""}],"value":"[:key1 \"hello\"]"}],"value":"{:key2 \"world\", :key1 \"hello\"}"},{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"},{"type":"html","content":"<span class='clj-keyword'>:my</span>","value":":my"}],"value":"#{\"words\" {:key2 \"world\", :key1 \"hello\"} 25 :my}"}],"value":"[(1 2) #{\"words\" {:key2 \"world\", :key1 \"hello\"} 25 :my}]"}
;; <=

;; **
;;; ## Functions
;;; 
;;; Clojure comes with a rich library of standard functions.  As mentioned earlier, you use functions by writing code using list sytax.  For example, 'str' is a Clojure function to print any value as a string.  To call it, you'd write (str *a variable name goes here*).
;; **

;; @@
(str 1)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;1&quot;</span>","value":"\"1\""}
;; <=

;; @@
(str #{1 2})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;#{1 2}&quot;</span>","value":"\"#{1 2}\""}
;; <=

;; @@
(str ['a 'b 'c])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;[a b c]&quot;</span>","value":"\"[a b c]\""}
;; <=

;; **
;;; BTW, str, like many Clojure functions, takes a variable number of arguments (variable arity).  Pass as much stuff to it as you want.
;; **

;; @@
(str 1 "hello" #{:key 12} [\a "b" 134])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;1hello#{:key 12}[\\\\a \\&quot;b\\&quot; 134]&quot;</span>","value":"\"1hello#{:key 12}[\\\\a \\\"b\\\" 134]\""}
;; <=

;; @@
(str)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;&quot;</span>","value":"\"\""}
;; <=

;; **
;;; Every function call in Clojure is denoted by list syntax where the first element in a list is the function you are calling and the remaining elements in the list are the arguments to the function.  There are no exceptions.  Every function is called this way.  This is both restrictive and blessedly uniform.  
;;; 
;;; If the first element in your list is not a function, you'll get an error.
;; **

;; @@
(1 2 3)
;; @@

;; **
;;; ^^ Ugly error message, isn't it?  This is one of Clojure's warts.
;;; 
;;; One consequence of Clojure's function syntax is that math operations look ugly (at least, I think most people would say it looks ugly).
;; **

;; @@
(+ 2 (/ 12 (- 6 3)))  ;;  2 + (12 / (6 - 3))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>6</span>","value":"6"}
;; <=

;; **
;;; It's not all bad though; variable arity makes certain things nicer.
;; **

;; @@
(+ 3 2 13 2 4) ;; 3 + 2 + 13 + 2 + 4
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>24</span>","value":"24"}
;; <=

;; **
;;; ## Defining Variables
;; **

;; **
;;; Variables are defined using 'def'.  They are immutable.
;; **

;; @@
(def x 12)

x

(+ x 2)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>14</span>","value":"14"}
;; <=

;; **
;;; def is really meant for top-level value definitions.  You shouldn't (can't?) use def arbitrarily within, for example, a function definition.  Speaking of functions...
;;; 
;;; ## Defining Functions
;;; 
;;; Clojure is not a pure functional programming, but it definitely encourages a functional programming style.  A key part of being a functional programming language is being able to create anonymous functions.  Our (short) path in learning how to define a function will therefore look like this:
;;; 
;;; anonymous function syntax => define a function with 'def' => define a function with 'defn'.
;;; 
;;; Anonymous functions are created with the 'fn' identifier.  More specifically, 'fn' must appear as the first element in a list. The next element is a vector listing the arguments to the function.  The remaining elements of the list are arbritrary bits of Clojure code.  It's easier than it sounds. 
;; **

;; @@
(fn [] "hello world")  ;; an anonymous function with no arguments that returns "hello world"
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;user$eval6415$fn__6416 user$eval6415$fn__6416@5e3e957c&gt;</span>","value":"#<user$eval6415$fn__6416 user$eval6415$fn__6416@5e3e957c>"}
;; <=

;; **
;;; Since it's anonymous, to actually call it, wrap the definition in an extra set of parens.
;; **

;; @@
((fn [] "hello world"))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;hello world&quot;</span>","value":"\"hello world\""}
;; <=

;; @@
( (fn [x] (+ 2 x))  ;;our function adds 2 to its single argument input
  12  ;;here we're applying it directly to 12
  )
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>14</span>","value":"14"}
;; <=

;; @@
((fn [x y]  (+ 10 x y)) 3 4)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>17</span>","value":"17"}
;; <=

;; **
;;; To give a name to a function, just use 'def'.
;; **

;; @@
(def foo (fn [x] 
           (+ x 2)))

(foo 12)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>14</span>","value":"14"}
;; <=

;; **
;;; 'defn' is a shorthand way to write the above. 
;; **

;; @@
(defn foo [x]
  (+ x 2))

(foo 12)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>14</span>","value":"14"}
;; <=

;; **
;;; And that's all there is to it.  Actually, not true.  Clojure provides a ton of features when it comes to defining functions including but not limited to:
;;; 
;;; * variable arity
;;; * multiple arity
;;; * keyword arguments
;;; * destructuring capabilities (e.g. if you're passed a map, and you're expecting a particular key, you can specify that in the argument list of the function definition)
;;; * various types of metadata
;;; 
;;; But all of that knowledge can be acquired as needed.  Using just the basic function definition you can do quite a lot.
;; **

;; @@

;; @@
